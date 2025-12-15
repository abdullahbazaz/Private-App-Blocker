package com.appblocker.utils

import android.content.Context
import android.content.SharedPreferences

class AppBlockerPreferences(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFS_NAME = "app_blocker_prefs"
        private const val KEY_BLOCKING_ENABLED = "blocking_enabled"
        private const val KEY_BLOCKED_APPS = "blocked_apps"
        
        // Default blocked apps
        val DEFAULT_BLOCKED_APPS = setOf(
            "com.google.android.youtube",
            "com.instagram.android"
        )
    }

    fun isBlockingEnabled(): Boolean {
        return prefs.getBoolean(KEY_BLOCKING_ENABLED, false)
    }

    fun setBlockingEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_BLOCKING_ENABLED, enabled).apply()
    }

    fun getBlockedApps(): Set<String> {
        val savedApps = prefs.getStringSet(KEY_BLOCKED_APPS, null)
        return savedApps ?: DEFAULT_BLOCKED_APPS
    }

    fun setBlockedApps(apps: Set<String>) {
        prefs.edit().putStringSet(KEY_BLOCKED_APPS, apps).apply()
    }

    fun addBlockedApp(packageName: String) {
        val currentApps = getBlockedApps().toMutableSet()
        currentApps.add(packageName)
        setBlockedApps(currentApps)
    }

    fun removeBlockedApp(packageName: String) {
        val currentApps = getBlockedApps().toMutableSet()
        currentApps.remove(packageName)
        setBlockedApps(currentApps)
    }

    fun isAppBlocked(packageName: String): Boolean {
        return getBlockedApps().contains(packageName)
    }
}
