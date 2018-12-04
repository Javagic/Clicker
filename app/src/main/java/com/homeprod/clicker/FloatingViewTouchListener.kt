/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 04.12.18 19:52
 */

package com.homeprod.clicker

import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

class FloatingViewTouchListener(
    private val onMove: () -> Unit,
    private val params: WindowManager.LayoutParams
) : View.OnTouchListener {
    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0.toFloat()
    private var initialTouchY: Float = 0.toFloat()
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = params.x
                initialY = params.y
                initialTouchX = event.rawX
                initialTouchY = event.rawY
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                params.x = initialX + (event.rawX - initialTouchX).toInt()
                params.y = initialY + (event.rawY - initialTouchY).toInt()
                onMove()
            }
        }
        return false
    }
}