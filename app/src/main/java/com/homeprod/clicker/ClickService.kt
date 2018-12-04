/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 04.12.18 15:09
 */

package com.homeprod.clicker

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView
import android.widget.Toast


class ClickService : AccessibilityService() {
    private val params by lazy {
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
    }
    private val windowManager by lazy { getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private val floatingButton by lazy {
        ImageView(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            Log.i("ClickService", it.toString())
            it.source
        }
        rootInActiveWindow?.let {
            Log.i("ClickService", it.toString())
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()
        Toast.makeText(baseContext, "onCreate", Toast.LENGTH_LONG).show()

        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 100
        windowManager.addView(floatingButton, params)
        floatingButton.setOnTouchListener(
            FloatingViewTouchListener(
                { windowManager.updateViewLayout(floatingButton, params) }, params
            )
        )
        floatingButton.setImageResource(R.drawable.snipe)
        floatingButton.apply {
            layoutParams.height = 20
            layoutParams.height = 20
        }
    }


    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.v(TAG, "onServiceConnected")
        val info = AccessibilityServiceInfo()
        info.describeContents()
        info.flags = AccessibilityServiceInfo.DEFAULT or
                AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        serviceInfo = info
    }

    private fun startApp() {
        val launchIntent = packageManager.getLaunchIntentForPackage("com.homeprod.clicker")
        launchIntent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(launchIntent)
    }

    override fun onInterrupt() {}
    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(floatingButton)
    }

}