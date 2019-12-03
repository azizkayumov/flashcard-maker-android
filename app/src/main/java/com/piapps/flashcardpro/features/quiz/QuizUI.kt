package com.piapps.flashcardpro.features.quiz

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.setIconColor
import jp.wasabeef.recyclerview.animators.LandingAnimator
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by abduaziz on 2019-10-29 at 13:11.
 */

class QuizUI : AnkoComponent<QuizFragment> {

    override fun createView(ui: AnkoContext<QuizFragment>) = with(ui) {
        frameLayout {
            backgroundColorResource = owner.theme.colorBackground

            ui.owner.ivClose = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56))
                padding = dip(16)
                imageResource = R.drawable.ic_close
                setIconColor(ctx, owner.theme.colorIconActive)
                setOnClickListener { ui.owner.close() }
            }

            ui.owner.rv = recyclerView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    verticalMargin = dip(56)
                }
                itemAnimator = LandingAnimator()
                ui.owner.layoutManager = LinearLayoutManager(ctx, HORIZONTAL, false)
                layoutManager = ui.owner.layoutManager
                adapter = ui.owner.adapter
                ui.owner.snapHelper.attachToRecyclerView(this)
                addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                        return true
                    }
                })
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            ui.owner.setOnCardScrolled()
                        }
                    }
                })
            }

            linearLayout {
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                }

                ui.owner.fabWrong = floatingActionButton {
                    layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                        margin = dip(16)
                    }
                    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, owner.theme.colorAccent))
                    rippleColor = ContextCompat.getColor(ui.ctx, owner.theme.colorDivider)
                    imageResource = R.drawable.ic_eye
                    setIconColor(ctx, owner.theme.white)
                }

                ui.owner.fabOk = floatingActionButton {
                    layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                        margin = dip(16)
                    }
                    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.md_green_500))
                    rippleColor = ContextCompat.getColor(ui.ctx, owner.theme.colorDivider)
                    imageResource = R.drawable.ic_check
                    setIconColor(ctx, owner.theme.white)
                }
            }

            ui.owner.tvCurrentCard = textView {
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                    margin = dip(16)
                }
                textSize = 12F
                textColorResource = owner.theme.colorSecondaryText
            }
        }
    }

}