package com.piapps.flashcardpro.features.editor.adapter.cells

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appTheme

/**
 * Created by abduaziz on 2019-10-01 at 20:38.
 */

class CardUI {

    companion object {
        val rootId = 9

        val frontId = 10
        val frontTvId = 11
        val frontIvId = 12
        val frontIvFlipId = 13

        val backId = 20
        val backTvId = 21
        val backIvId = 22
        val backIvFlipId = 23

        val ivEditId = 100
        val ivDeleteId = 101
        val ivSelectedIndicatorId = 102
    }

    fun createView(ctx: Context) = with(ctx) {
        frameLayout {
            lparams(matchParent, matchParent)

            cardView {
                id = rootId
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(32)
                    bottomMargin = dip(32)
                    leftMargin = dip(8)
                    rightMargin = dip(8)
                }

                // front
                frameLayout {
                    id = frontId
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)

                    imageView {
                        id = frontIvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    }

                    textView {
                        id = frontTvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                            topMargin = dip(48)
                            bottomMargin = dip(48)
                        }
                        padding = dip(16)
                        textSize = 28F
                        textColorResource = ctx.appTheme().colorPrimaryText
                        gravity = Gravity.CENTER
                    }

                    imageView {
                        id = frontIvFlipId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                            gravity = Gravity.END or Gravity.BOTTOM
                        }
                        padding = dip(12)
                        setImageResource(R.drawable.ic_flip)
                        setIconColor(ContextCompat.getColor(ctx, ctx.appTheme().colorIconActive))
                        setRippleEffect()
                    }
                }

                // back
                frameLayout {
                    id = backId
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    visibility = View.GONE

                    imageView {
                        id = backIvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    }

                    textView {
                        id = backTvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                            topMargin = dip(48)
                            bottomMargin = dip(48)
                        }
                        padding = dip(16)
                        textSize = 28F
                        textColorResource = ctx.appTheme().colorPrimaryText
                        gravity = Gravity.CENTER
                    }

                    imageView {
                        id = backIvFlipId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                            gravity = Gravity.END or Gravity.BOTTOM
                        }
                        padding = dip(12)
                        setImageResource(R.drawable.ic_flip)
                        setIconColor(ContextCompat.getColor(ctx, ctx.appTheme().colorIconActive))
                        setRippleEffect()
                    }
                }

                imageView {
                    id = ivEditId
                    layoutParams = FrameLayout.LayoutParams(dip(48), dip(48))
                    padding = dip(12)
                    setImageResource(R.drawable.ic_edit)
                    setIconColor(ContextCompat.getColor(ctx, ctx.appTheme().colorIconActive))
                    setRippleEffect()
                }

                imageView {
                    id = ivDeleteId
                    layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                        gravity = Gravity.END
                    }
                    padding = dip(12)
                    setImageResource(R.drawable.ic_archive)
                    setIconColor(ctx, ctx.appTheme().colorIconActive)
                    setRippleEffect()
                }

                imageView {
                    id = ivSelectedIndicatorId
                    layoutParams = FrameLayout.LayoutParams(dip(24), dip(24)).apply {
                        gravity = Gravity.BOTTOM
                        margin = dip(12)
                    }
                    setImageResource(R.drawable.ic_check)
                    setIconColor(ContextCompat.getColor(ctx, ctx.appTheme().colorIconActive))
                }
            }
        }
    }
}
