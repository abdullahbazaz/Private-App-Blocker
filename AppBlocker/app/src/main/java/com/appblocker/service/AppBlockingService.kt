package com.appblocker.service

import android.accessibilityservice.AccessibilityService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import androidx.core.app.NotificationCompat
import com.appblocker.BlockOverlayActivity
import com.appblocker.R
import com.appblocker.utils.AppBlockerPreferences
import kotlinx.coroutines.*

class AppBlockingService : AccessibilityService() {

    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var monitoringJob: Job? = null
    private lateinit var preferences: AppBlockerPreferences
    
    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "app_blocker_channel"
        private const val CHECK_INTERVAL = 1000L // Check every second
        
        // Target app packages
        private val BLOCKED_APPS = setOf(
            "com.google.android.youtube", // YouTube (includes Shorts)
            "com.instagram.android" // Instagram (includes Reels)
        )
    }

    override fun onCreate() {
        super.onCreate()
        preferences = AppBlockerPreferences(this)
        createNotificationChannel()
        startForegroundService()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        startMonitoring()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || !preferences.isBlockingEnabled()) return

        event.packageName?.toString()?.let { packageName ->
            checkAndBlockApp(packageName)
        }
    }

    override fun onInterrupt() {
        // Handle interruption
    }

    private fun startMonitoring() {
        monitoringJob?.cancel()
        monitoringJob = serviceScope.launch {
            while (isActive) {
                if (preferences.isBlockingEnabled()) {
                    checkCurrentApp()
                }
                delay(CHECK_INTERVAL)
            }
        }
    }

    private fun checkCurrentApp() {
        val currentApp = getCurrentAppPackage()
        currentApp?.let { packageName ->
            checkAndBlockApp(packageName)
        }
    }

    private fun getCurrentAppPackage(): String? {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) 
            as? android.app.usage.UsageStatsManager ?: return null

        val currentTime = System.currentTimeMillis()
        val stats = usageStatsManager.queryUsageStats(
            android.app.usage.UsageStatsManager.INTERVAL_DAILY,
            currentTime - 1000 * 10,
            currentTime
        )

        return stats?.maxByOrNull { it.lastTimeUsed }?.packageName
    }

    private fun checkAndBlockApp(packageName: String) {
        val blockedApps = preferences.getBlockedApps()
        
        if (blockedApps.contains(packageName)) {
            blockApp(packageName)
        }
    }

    private fun blockApp(packageName: String) {
        val intent = Intent(this, BlockOverlayActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or 
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NO_HISTORY
            putExtra("blocked_package", packageName)
        }
        startActivity(intent)
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("App Blocker Active")
            .setContentText("Monitoring and blocking apps")
            .setSmallIcon(R.drawable.ic_block)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "App Blocker Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps the app blocker running"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        monitoringJob?.cancel()
        serviceScope.cancel()
    }
}
