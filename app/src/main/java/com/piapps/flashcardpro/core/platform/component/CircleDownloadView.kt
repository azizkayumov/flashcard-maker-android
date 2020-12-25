package com.piapps.flashcardpro.core.platform.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.kent.layouts.dip
import com.piapps.flashcardpro.R

/**
 * Created by abduaziz on 6/16/18.
 */

class CircleDownloadView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    val circleProgressBar: CircleProgressBar
    val imageView: AppCompatImageView

    var isProgressing = false
    var hand = Handler(Looper.getMainLooper())

    init {
        isProgressing = false

        circleProgressBar =
            CircleProgressBar(context, attrs)
        circleProgressBar.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(circleProgressBar)

        imageView = AppCompatImageView(context, attrs)
        imageView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(imageView)
    }

    val runnable = object : Runnable {
        override fun run() {
            if (isProgressing) {
                circleProgressBar.startAngle += 1F
                if (circleProgressBar.startAngle > 360) circleProgressBar.startAngle = 0f
                invalidate()
                requestLayout()
                circleProgressBar.forceLayout()
            }
            hand.postDelayed(this, 50)
        }
    }

    fun setProgress(progress: Long, max: Long, imageRes: Int) {
        circleProgressBar.max = max.toDouble()
        circleProgressBar.pro = progress.toDouble()
        imageView.setImageResource(imageRes)
        isProgressing = true

        hand.removeCallbacks(runnable)
        hand.post(runnable)

        if (progress == max) {
            isProgressing = false
            hand.removeCallbacks(runnable)
        }
        invalidate()
        requestLayout()
        circleProgressBar.forceLayout()
    }

    class CircleProgressBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {
        var strokeWidth = dip(2).toFloat()
        var pro: Double = 0.0
        var max: Double = 100.0

        var startAngle = -90F
        var rectF: RectF
        var foregroundPaint: Paint

        init {
            rectF = RectF()
            foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            foregroundPaint.color = ContextCompat.getColor(
                context,
                R.color.colorPrimary
            )//ContextCompat.getColor(context, R.color.colorPrimary)
            foregroundPaint.style = Paint.Style.STROKE
            foregroundPaint.strokeWidth = strokeWidth
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
            val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
            val min = Math.min(width, height)
            setMeasuredDimension(min, min)
            rectF.set(0 + strokeWidth, 0 + strokeWidth, min - strokeWidth, min - strokeWidth)
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            var angle = (360 * pro.div(max)).toFloat()
            if (angle > 360) angle = 360.toFloat()
            canvas?.drawArc(rectF, startAngle, angle, false, foregroundPaint)
        }
    }
}
