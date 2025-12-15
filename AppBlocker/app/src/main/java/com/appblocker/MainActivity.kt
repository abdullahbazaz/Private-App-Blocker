package com.appblocker

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.appblocker.adapter.BlockedAppsAdapter
import com.appblocker.databinding.ActivityMainBinding
import com.appblocker.model.AppInfo
import com.appblocker.utils.AppBlockerPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: AppBlockerPreferences
    private lateinit var adapter: BlockedAppsAdapter
    private val installedApps = mutableListOf<AppInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = AppBlockerPreferences(this)
        
        setupUI()
        checkPermissions()
        loadInstalledApps()
    }

    private fun setupUI() {
        // Set up the blocking switch
        binding.switchBlockingEnabled.isChecked = preferences.isBlockingEnabled()
        binding.switchBlockingEnabled.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !hasAllPermissions()) {
                binding.switchBlockingEnabled.isChecked = false
                Toast.makeText(this, "Please enable all required permissions", Toast.LENGTH_LONG).show()
                checkPermissions()
            } else {
                preferences.setBlockingEnabled(isChecked)
                updateServiceStatus()
            }
        }

        // Set up RecyclerView for blocked apps
        adapter = BlockedAppsAdapter(installedApps) { appInfo, isBlocked ->
            if (isBlocked) {
                preferences.addBlockedApp(appInfo.packageName)
            } else {
                preferences.removeBlockedApp(appInfo.packageName)
            }
        }
        
        binding.recyclerViewApps.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewApps.adapter = adapter

        // Permissions button
        binding.btnPermissions.setOnClickListener {
            checkPermissions()
        }
    }

    private fun loadInstalledApps() {
        installedApps.clear()
        val pm = packageManager
        val packages = pm.getInstalledApplications(0)

        for (packageInfo in packages) {
            // Filter to show only user-installed or popular apps
            if (packageInfo.packageName.contains("youtube") || 
                packageInfo.packageName.contains("instagram") ||
                packageInfo.packageName.contains("facebook") ||
                packageInfo.packageName.contains("tiktok") ||
                packageInfo.packageName.contains("twitter") ||
                packageInfo.packageName.contains("snapchat")) {
                
                val appName = pm.getApplicationLabel(packageInfo).toString()
                val icon = pm.getApplicationIcon(packageInfo)
                val isBlocked = preferences.isAppBlocked(packageInfo.packageName)
                
                installedApps.add(AppInfo(appName, packageInfo.packageName, icon, isBlocked))
            }
        }

        installedApps.sortBy { it.appName }
        adapter.notifyDataSetChanged()
    }

    private fun checkPermissions() {
        val hasAccessibility = isAccessibilityServiceEnabled()
        val hasUsageStats = hasUsageStatsPermission()
        val hasOverlay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else true

        binding.textAccessibilityStatus.text = if (hasAccessibility) "✓ Enabled" else "✗ Disabled"
        binding.textUsageStatsStatus.text = if (hasUsageStats) "✓ Enabled" else "✗ Disabled"
        binding.textOverlayStatus.text = if (hasOverlay) "✓ Enabled" else "✗ Disabled"

        // Set up permission request buttons
        binding.btnAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        binding.btnUsageStats.setOnClickListener {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        binding.btnOverlay.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }
    }

    private fun hasAllPermissions(): Boolean {
        return isAccessibilityServiceEnabled() && 
               hasUsageStatsPermission() && 
               (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this))
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val service = "${packageName}/.service.AppBlockingService"
        val enabledServices = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        return enabledServices?.contains(service) == true
    }

    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                packageName
            )
        } else {
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun updateServiceStatus() {
        // The service is automatically started when accessibility is enabled
        // This just updates the UI
        val status = if (preferences.isBlockingEnabled()) {
            "App Blocker is ACTIVE"
        } else {
            "App Blocker is INACTIVE"
        }
        binding.textStatus.text = status
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
        updateServiceStatus()
    }
}
