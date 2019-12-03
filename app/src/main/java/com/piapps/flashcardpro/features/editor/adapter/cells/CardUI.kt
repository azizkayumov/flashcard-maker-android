package com.piapps.flashcardpro.features.editor.adapter.cells

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Created by abduaziz on 2019-10-01 at 20:38.
 */

class CardUI : AnkoComponent<ViewGroup> {

    companion object {
        val rootId = 9

        val frontId = 10
        val frontTvId = 11
        val frontIvId = 12
        val frontIvEditId = 13
        val frontIvFlipId = 14
        val frontIvDeleteId = 15

        val backId = 20
        val backTvId = 21
        val backIvId = 22
        val backIvEditId = 23
        val backIvFlipId = 24
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)

            cardView {
                id = rootId
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    verticalMargin = dip(32)
                    horizontalMargin = dip(16)
                }

                // front
                frameLayout {
                    id = frontId
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)

                    appCompatImageView {
                        id = frontIvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    }

                    appCompatImageView {
                        id = frontIvEditId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48))
                        padding = dip(12)
                        imageResource = R.drawable.ic_add
                        setIconColor(ctx, R.color.colorIconActive)
                        setRippleEffect()
                    }

                    appCompatImageView {
                        id = frontIvDeleteId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                            gravity = Gravity.END
                        }
                        padding = dip(12)
                        imageResource = R.drawable.ic_close
                        setIconColor(ctx, R.color.colorIconActive)
                        setRippleEffect()
                    }

                    appCompatImageView {
                        id = frontIvFlipId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                            gravity = Gravity.END or Gravity.BOTTOM
                        }
                        padding = dip(12)
                        imageResource = R.drawable.ic_flip
                        setIconColor(ctx, R.color.colorIconActive)
                    }

                    autoResizeTextView {
                        id = frontTvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                            verticalMargin = dip(48)
                        }
                        padding = dip(16)
                        textSize = 32F
                        textColorResource = ctx.appTheme().colorPrimaryText
                        gravity = Gravity.CENTER
                    }
                }

                // back
                frameLayout {
                    id = backId
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    visibility = View.GONE

                    appCompatImageView {
                        id = backIvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    }

                    appCompatImageView {
                        id = backIvEditId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48))
                        padding = dip(12)
                        imageResource = R.drawable.ic_add
                        setIconColor(ctx, R.color.colorIconActive)
                        setRippleEffect()
                    }

                    appCompatImageView {
                        id = backIvFlipId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                            gravity = Gravity.END or Gravity.BOTTOM
                        }
                        padding = dip(12)
                        imageResource = R.drawable.ic_flip
                        setIconColor(ctx, R.color.colorIconActive)
                        setRippleEffect()
                    }

                    autoResizeTextView {
                        id = backTvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                            verticalMargin = dip(48)
                        }
                        padding = dip(16)
                        textSize = 32F
                        textColorResource = ctx.appTheme().colorPrimaryText
                        gravity = Gravity.CENTER
                    }
                }
            }
        }
    }

}