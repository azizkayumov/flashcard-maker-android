package com.piapps.flashcardpro.features.quiz

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.horizontalLayout
import com.piapps.flashcardpro.R
import jp.wasabeef.recyclerview.animators.LandingAnimator

/**
 * Created by abduaziz on 2019-10-29 at 13:11.
 */

fun QuizFragment.UI(): View {
    return ctx.frameLayout {
        backgroundColorResource = theme.colorBackground

        ivClose = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56))
            padding = dip(16)
            setImageResource(R.drawable.ic_arrow_back)
            setIconColor(ctx, theme.colorIconActive)
            setOnClickListener { close() }
        }

        rv = recyclerView {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(56)
                bottomMargin = dip(56)
            }
            itemAnimator = LandingAnimator()
            this@UI.layoutManager = LinearLayoutManager(ctx, HORIZONTAL, false)
            layoutManager = this@UI.layoutManager
            adapter = this@UI.adapter
            snapHelper.attachToRecyclerView(this)
            addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return true
                }
            })
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        setOnCardScrolled()
                    }
                }
            })
        }

        tvCurrentCard = textView {
            layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                margin = dip(16)
            }
            textSize = 12F
            textColorResource = theme.colorSecondaryText
        }

        ivWrong = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.BOTTOM or Gravity.START
            }
            padding = dip(16)
            setImageResource(R.drawable.ic_flip)
            setIconColor(ctx, theme.colorIconActive)
            setRippleEffectBorderless()
        }

        ivRight = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.BOTTOM or Gravity.END
            }
            padding = dip(16)
            setImageResource(R.drawable.ic_check)
            setIconColor(ctx, theme.colorIconActive)
            setRippleEffectBorderless()
        }
    }
}