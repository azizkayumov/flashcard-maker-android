package com.piapps.flashcardpro.features.editor

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by abduaziz on 2019-09-30 at 21:33.
 */

class SetUI : AnkoComponent<SetFragment> {

    override fun createView(ui: AnkoContext<SetFragment>) = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = owner.theme.colorBackground

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.untitled_set))
                (tvTitle.layoutParams as FrameLayout.LayoutParams).apply {
                    marginEnd = dip(112)
                }
                onBackClick { owner.close() }
            }

            ui.owner.ivSetColor = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    marginEnd = dip(56)
                    gravity = Gravity.END
                }
                padding = dip(16)
                imageResource = R.drawable.ic_color
                setIconColor(ctx, owner.theme.colorIconActive)
                setRippleEffect()
            }

            ui.owner.rv = recyclerView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    verticalMargin = dip(56)
                }
                itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f)).apply {
                    addDuration = 200
                    changeDuration = 0
                }
                itemAnimator = LandingAnimator()
                clipToPadding = false
                padding = dip(32)
                ui.owner.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                layoutManager = ui.owner.layoutManager
                adapter = ui.owner.adapter
                ui.owner.snapHelper.attachToRecyclerView(this)
                addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE){
                            ui.owner.setOnCardScrolled()
                        }
                    }
                })
            }

            ui.owner.rvLabels = recyclerView {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    topMargin = dip(56)
                }
                layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                leftPadding = dip(4)
                rightPadding = dip(56)
                clipToPadding = false
            }

            linearLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56)).apply {
                    gravity = Gravity.BOTTOM
                }
                backgroundColorResource = owner.theme.colorPrimary

                ui.owner.ivBottomMenu = appCompatImageView {
                    layoutParams = FrameLayout.LayoutParams(dip(56), dip(56))
                    padding = dip(16)
                    imageResource = R.drawable.ic_navigation_menu
                    setIconColor(ctx, owner.theme.colorIconActive)
                    setRippleEffect()
                    setOnClickListener { ui.owner.showMoreOptions() }
                }

                ui.owner.ivStudy = appCompatImageView {
                    layoutParams = FrameLayout.LayoutParams(dip(56), dip(56))
                    padding = dip(16)
                    imageResource = R.drawable.ic_study
                    setIconColor(ctx, owner.theme.colorIconActive)
                    setRippleEffect()
                }

                ui.owner.ivQuiz = appCompatImageView {
                    layoutParams = FrameLayout.LayoutParams(dip(56), dip(56))
                    padding = dip(16)
                    imageResource = R.drawable.ic_quiz_me
                    setIconColor(ctx, owner.theme.colorIconActive)
                    setRippleEffect()
                }
            }

            view {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                    bottomMargin = dip(56)
                    gravity = Gravity.BOTTOM
                }
                backgroundResource = R.drawable.pre_lollipop_elevation_up
            }

            ui.owner.fab = floatingActionButton {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.BOTTOM or Gravity.END
                    bottomMargin = dip(28)
                    rightMargin = dip(16)
                }
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, owner.theme.colorAccent))
                rippleColor = ContextCompat.getColor(ui.ctx, owner.theme.colorDivider)
                imageResource = R.drawable.ic_add
                setIconColor(ctx, owner.theme.white)
            }

            view {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                    topMargin = dip(56)
                }
                backgroundResource = R.drawable.pre_lollipop_elevation
            }


            ui.owner.tvCurrentCard = textView {
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.BOTTOM
                    bottomMargin = dip(64)
                    horizontalMargin = dip(16)
                }
                textSize = 12F
                textColorResource = owner.theme.colorSecondaryText
            }
        }
    }

}