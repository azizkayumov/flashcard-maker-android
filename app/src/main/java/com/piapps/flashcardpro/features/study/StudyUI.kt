package com.piapps.flashcardpro.features.study

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.setIconColor
import com.piapps.flashcardpro.core.extension.setRippleEffectBorderless
import jp.wasabeef.recyclerview.animators.LandingAnimator
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by abduaziz on 2019-10-29 at 13:02.
 */

class StudyUI : AnkoComponent<StudyFragment> {

    override fun createView(ui: AnkoContext<StudyFragment>) = with(ui) {
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
                ui.owner.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                layoutManager = ui.owner.layoutManager
                adapter = ui.owner.adapter
                ui.owner.snapHelper.attachToRecyclerView(this)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            ui.owner.setOnCardScrolled()
                        }
                    }
                })
            }

            ui.owner.ivPrevious = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.BOTTOM or Gravity.START
                }
                padding = dip(16)
                imageResource = R.drawable.ic_arrow_back
                setIconColor(ctx, owner.theme.colorIconActive)
                setRippleEffectBorderless()
            }

            ui.owner.ivFlip = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.BOTTOM or Gravity.CENTER
                }
                padding = dip(16)
                imageResource = R.drawable.ic_flip
                setIconColor(ctx, owner.theme.colorIconActive)
                setRippleEffectBorderless()
            }

            ui.owner.ivNext = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.BOTTOM or Gravity.END
                }
                padding = dip(16)
                imageResource = R.drawable.ic_arrow_forward
                setIconColor(ctx, owner.theme.colorIconActive)
                setRippleEffectBorderless()
            }

            ui.owner.ivShuffle = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.END
                }
                padding = dip(16)
                imageResource = R.drawable.ic_shuffle
                setIconColor(ctx, owner.theme.colorIconActive)
                setRippleEffectBorderless()
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