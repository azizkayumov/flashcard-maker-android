package com.piapps.flashcardpro.features.editor

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

/**
 * Created by abduaziz on 2019-09-30 at 21:33.
 */

fun SetFragment.UI(): View {
    return ctx.frameLayout {
        lparams(matchParent, matchParent)
        backgroundColorResource = theme.colorBackground

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)

            setTitle(ctx.getLocalizedString(R.string.untitled_set))
            (tvTitle.layoutParams as FrameLayout.LayoutParams).apply {
                marginEnd = dip(112)
            }
            onBackClick { close() }
        }

        ivSetColor = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                marginEnd = dip(56)
                gravity = Gravity.END
            }
            padding = dip(16)
            setImageResource(R.drawable.ic_color)
            setIconColor(ctx, theme.colorIconActive)
            setRippleEffect()
        }

        rv = recyclerView {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(56)
                bottomMargin = dip(56)
            }
            itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f)).apply {
                addDuration = 200
                changeDuration = 0
            }
            itemAnimator = LandingAnimator()
            clipToPadding = false
            padding = dip(32)
            this@UI.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = this@UI.layoutManager
            adapter = this@UI.adapter
            snapHelper.attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        setOnCardScrolled()
                    }
                }
            })
        }

        rvLabels = recyclerView {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                topMargin = dip(56)
            }
            layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
            leftPadding = dip(4)
            rightPadding = dip(56)
            clipToPadding = false
        }

        horizontalLayout {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56)).apply {
                gravity = Gravity.BOTTOM
            }
            backgroundColorResource = theme.colorPrimary

            ivBottomMenu = imageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_navigation_menu)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
                setOnClickListener { showMoreOptions() }
            }

            ivStudy = imageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_study)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
            }

            ivQuiz = imageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_quiz_me)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
            }
        }

        view {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                bottomMargin = dip(56)
                gravity = Gravity.BOTTOM
            }
            setBackgroundResource(R.drawable.pre_lollipop_elevation_up)
        }

        fab = floatingActionButton {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.BOTTOM or Gravity.END
                bottomMargin = dip(28)
                rightMargin = dip(16)
            }
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, theme.colorAccent))
            rippleColor = ContextCompat.getColor(ctx, theme.colorDivider)
            setImageResource(R.drawable.ic_add)
            setIconColor(ctx, theme.white)
        }

        view {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                topMargin = dip(56)
            }
            setBackgroundResource(R.drawable.pre_lollipop_elevation)
        }


        tvCurrentCard = textView {
            layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                gravity = Gravity.BOTTOM
                bottomMargin = dip(64)
                rightMargin = dip(16)
                leftMargin = dip(16)
            }
            textSize = 12F
            textColorResource = theme.colorSecondaryText
        }
    }
}