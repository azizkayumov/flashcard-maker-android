package com.piapps.flashcardpro.features.editor.adapter.cells

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.autoResizeTextView
import com.piapps.flashcardpro.R

/**
 * Created by abduaziz on 2019-10-01 at 20:38.
 */

class CardUI {

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

    fun createView(ctx: Context) = with(ctx) {
        frameLayout {
            lparams(matchParent, matchParent)

            cardView {
                id = rootId
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(32)
                    bottomMargin = dip(32)
                    leftMargin = dip(16)
                    rightMargin = dip(16)
                }

                // front
                frameLayout {
                    id = frontId
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)

                    imageView {
                        id = frontIvId
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    }

                    imageView {
                        id = frontIvEditId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48))
                        padding = dip(12)
                        setImageResource(R.drawable.ic_add)
                        setIconColor(ctx, R.color.colorIconActive)
                        setRippleEffect()
                    }

                    imageView {
                        id = frontIvDeleteId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                            gravity = Gravity.END
                        }
                        padding = dip(12)
                        setImageResource(R.drawable.ic_close)
                        setIconColor(ctx, R.color.colorIconActive)
                        setRippleEffect()
                    }

                    imageView {
                        id = frontIvFlipId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                            gravity = Gravity.END or Gravity.BOTTOM
                        }
                        padding = dip(12)
                        setImageResource(R.drawable.ic_flip)
                        setIconColor(ctx, R.color.colorIconActive)
                    }

                    autoResizeTextView {
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

                    imageView {
                        id = backIvEditId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48))
                        padding = dip(12)
                        setImageResource(R.drawable.ic_add)
                        setIconColor(ctx, R.color.colorIconActive)
                        setRippleEffect()
                    }

                    imageView {
                        id = backIvFlipId
                        layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                            gravity = Gravity.END or Gravity.BOTTOM
                        }
                        padding = dip(12)
                        setImageResource(R.drawable.ic_flip)
                        setIconColor(ctx, R.color.colorIconActive)
                        setRippleEffect()
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
                }
            }
        }
    }

}
