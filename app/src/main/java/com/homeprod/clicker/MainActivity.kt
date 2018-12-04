package com.homeprod.clicker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var OVERLAY_PERMISSION_REQ_CODE = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAccess()
        checkPermission()
        btnStart.setOnClickListener {
            startService(Intent(this, ClickService::class.java))
        }
        btnStop.setOnClickListener {
            stopService(Intent(this, ClickService::class.java))
        }
    }

    protected fun checkAccess(): Boolean {
        val string = getString(R.string.accessibilityservice_id)
        for (id in (getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager).getEnabledAccessibilityServiceList(
            AccessibilityEvent.TYPES_ALL_MASK
        )) {
            if (string == id.id) {
                return true
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT > 22 && !Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
            }
        }
    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this@MainActivity)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
        }

    }

}
