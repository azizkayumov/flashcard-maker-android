package com.piapps.flashcardpro.core.platform

import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View


class OnMotionListener(val onPress: () -> Unit,
                       val onSingleClick: () -> Unit) : View.OnTouchListener{

    companion object{
        const val LONG_PRESS_DELTA = 500L
        const val PRESS_REPEAT_DELTA = 250L
    }

    private var isPressing = false
    private var lastMotionDownActionTime = Long.MAX_VALUE

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable{
        override fun run() {
            isPressing = true
            onPress()
            handler.postDelayed(this, PRESS_REPEAT_DELTA)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                lastMotionDownActionTime = System.currentTimeMillis()
                // start the timer
                handler.postDelayed(runnable, LONG_PRESS_DELTA)
            }
            MotionEvent.ACTION_UP -> {
                handler.removeCallbacks(runnable)

                if (isPressing){
                    isPressing = false
                }else{
                    onSingleClick()
                }
            }
            else -> return false
        }
        return true
    }
}