package com.appblocker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.appblocker.databinding.ActivityBlockOverlayBinding

class BlockOverlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlockOverlayBinding
    private val handler = Handler(Looper.getMainLooper())
    private var blockedPackage: String? = null

    companion object {
        private const val AUTO_CLOSE_DELAY = 2000L // 2 seconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockOverlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        blockedPackage = intent.getStringExtra("blocked_package")
        
        setupUI()
        scheduleAutoClose()
    }

    private fun setupUI() {
        val appName = getAppName(blockedPackage)
        binding.textBlockedApp.text = "Access to $appName is blocked"
        
        binding.btnGoBack.setOnClickListener {
            goToHome()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    private fun getAppName(packageName: String?): String {
        return when (packageName) {
            "com.google.android.youtube" -> "YouTube"
            "com.instagram.android" -> "Instagram"
            else -> packageName ?: "this app"
        }
    }

    private fun scheduleAutoClose() {
        handler.postDelayed({
            goToHome()
        }, AUTO_CLOSE_DELAY)
    }

    private fun goToHome() {
        val intent = android.content.Intent(android.content.Intent.ACTION_MAIN).apply {
            addCategory(android.content.Intent.CATEGORY_HOME)
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        // Prevent back button from returning to blocked app
        goToHome()
    }
}
