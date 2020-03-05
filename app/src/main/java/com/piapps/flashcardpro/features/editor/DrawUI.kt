package com.piapps.flashcardpro.features.editor

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.horizontalLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.drawView
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.rm.freedrawview.ResizeBehaviour

/**
 * Created by abduaziz on 2019-11-17 at 02:51.
 */

fun DrawFragment.UI(): View {
    return ctx.frameLayout {
        backgroundColorResource = theme.colorBackground

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)

            setTitle(ctx.getLocalizedString(R.string.drawing))
            onBackClick {
                close()
            }
        }

        drawView = drawView {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(56)
                bottomMargin = dip(56)
            }
            backgroundColorResource = theme.white
            paintAlpha = 255
            paintColor = ContextCompat.getColor(ctx, theme.black)
            setPaintWidthDp(dip(8).toFloat())
            resizeBehaviour = ResizeBehaviour.CROP
        }

        ivUndo = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.END
                rightMargin = dip(128)
            }
            padding = dip(16)
            setImageResource(R.drawable.ic_undo)
            setIconColor(ctx, theme.colorIconActive)
        }

        ivRedo = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.END
                rightMargin = dip(56)
            }
            padding = dip(16)
            setImageResource(R.drawable.ic_redo)
            setIconColor(ctx, theme.colorIconActive)
        }

        ivDone = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.END
            }
            padding = dip(16)
            setImageResource(R.drawable.ic_check)
            setIconColor(ctx, theme.colorIconActive)
        }

        horizontalLayout {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56)).apply {
                gravity = Gravity.BOTTOM
            }
            weightSum = 4F

            ivPencil = imageView {
                layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                    weight = 1F
                }
                padding = dip(16)
                setImageResource(R.drawable.ic_pencil)
                setIconColor(ctx, theme.colorIconActive)
            }

            ivPen = imageView {
                layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                    weight = 1F
                }
                padding = dip(16)
                setImageResource(R.drawable.ic_pen)
                setIconColor(ctx, theme.colorAccent)
            }

            ivBrush = imageView {
                layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                    weight = 1F
                }
                padding = dip(16)
                setImageResource(R.drawable.ic_brush)
                setIconColor(ctx, theme.colorIconActive)
            }

            ivColor = imageView {
                layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                    weight = 1F
                }
                padding = dip(16)
                setImageResource(R.drawable.ic_color)
                setIconColor(ctx, theme.black)
            }
        }

        // elevation
        view {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                bottomMargin = dip(56)
                gravity = Gravity.BOTTOM
            }
            setBackgroundResource(R.drawable.pre_lollipop_elevation_up)
        }

        // elevation
        view {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                topMargin = dip(56)
            }
            setBackgroundResource(R.drawable.pre_lollipop_elevation)
        }
    }
}