package com.piapps.flashcardpro.features.editor

import android.view.Gravity
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
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
                marginEnd = dip(72)
            }
            onBackClick { close() }
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
            this@UI.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = this@UI.layoutManager
            adapter = this@UI.adapter
            snapHelper.attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        showCurrentCardPosition()
                    }
                }
            })
        }

        rvLabels = recyclerView {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(32)).apply {
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
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_navigation_menu)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
            }

            ivStudy = imageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_study)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
            }

            ivQuiz = imageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_quiz_me)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
            }

            // Distance set-related buttons(quiz, study) from card-related buttons (add, next, prev)
            view {
                layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                    weight = 1F
                }
            }

            ivAdd = imageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_add)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
            }

            ivPrev = imageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_left)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
            }

            ivNext = imageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_right)
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

        llSelectionParent = horizontalLayout {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            visibility = View.GONE

            ivCancelSelection = imageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                setImageResource(R.drawable.ic_close)
                setIconColor(ctx, theme.colorIconActive)
                setRippleEffect()
            }

            tvSelectedCount = textView {
                layoutParams = LinearLayout.LayoutParams(wrapContent, matchParent)
                gravity = Gravity.CENTER
                text = "0"
                textColorResource = theme.colorPrimaryText
                textSize = 18F
                makeBold()
                setRippleEffect()
            }

            view {
                layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                    weight = 1F
                }
            }

            tvCut = textView {
                layoutParams = LinearLayout.LayoutParams(wrapContent, matchParent)
                gravity = Gravity.CENTER
                horizontalPadding = dip(16)
                text = ctx.getLocalizedString(R.string.cut).toUpperCase()
                textColorResource = theme.colorPrimaryText
                textSize = 15F
                makeBold()
                setRippleEffect()
            }

            tvCopy = textView {
                layoutParams = LinearLayout.LayoutParams(wrapContent, matchParent)
                gravity = Gravity.CENTER
                horizontalPadding = dip(16)
                text = ctx.getLocalizedString(R.string.copy).toUpperCase()
                textColorResource = theme.colorPrimaryText
                textSize = 15F
                makeBold()
                setRippleEffect()
            }
        }
    }
}
