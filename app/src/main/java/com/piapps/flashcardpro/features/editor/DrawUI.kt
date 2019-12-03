package com.piapps.flashcardpro.features.editor

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import com.rm.freedrawview.ResizeBehaviour
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-11-17 at 02:51.
 */

class DrawUI : AnkoComponent<DrawFragment> {

    override fun createView(ui: AnkoContext<DrawFragment>) = with(ui) {
        frameLayout {
            backgroundColorResource = owner.theme.colorBackground

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.drawing))
                onBackClick {
                    ui.owner.close()
                }
            }

            ui.owner.drawView = drawView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(56)
                    bottomMargin = dip(56)
                }
                backgroundColorResource = owner.theme.white
                paintAlpha = 255
                paintColor = ContextCompat.getColor(ctx, owner.theme.black)
                setPaintWidthDp(dip(8).toFloat())
                resizeBehaviour = ResizeBehaviour.CROP
            }

            ui.owner.ivUndo = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.RIGHT
                    rightMargin = dip(128)
                }
                padding = dip(16)
                imageResource = R.drawable.ic_undo
                setIconColor(ctx, owner.theme.colorIconActive)
            }

            ui.owner.ivRedo = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.RIGHT
                    rightMargin = dip(56)
                }
                padding = dip(16)
                imageResource = R.drawable.ic_redo
                setIconColor(ctx, owner.theme.colorIconActive)
            }

            ui.owner.ivDone = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.RIGHT
                }
                padding = dip(16)
                imageResource = R.drawable.ic_check
                setIconColor(ctx, owner.theme.colorIconActive)
            }


            linearLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56)).apply {
                    gravity = Gravity.BOTTOM
                }
                weightSum = 4F

                ui.owner.ivPencil = appCompatImageView {
                    layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                        weight = 1F
                    }
                    padding = dip(16)
                    imageResource = R.drawable.ic_pencil
                    setIconColor(ctx, owner.theme.colorIconActive)
                }

                ui.owner.ivPen = appCompatImageView {
                    layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                        weight = 1F
                    }
                    padding = dip(16)
                    imageResource = R.drawable.ic_pen
                    setIconColor(ctx, owner.theme.colorAccent)
                }

                ui.owner.ivBrush = appCompatImageView {
                    layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                        weight = 1F
                    }
                    padding = dip(16)
                    imageResource = R.drawable.ic_brush
                    setIconColor(ctx, owner.theme.colorIconActive)
                }

                ui.owner.ivColor = appCompatImageView {
                    layoutParams = LinearLayout.LayoutParams(0, dip(56)).apply {
                        weight = 1F
                    }
                    padding = dip(16)
                    imageResource = R.drawable.ic_color
                    setIconColor(ctx, owner.theme.black)
                }
            }

            // elevation
            view {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                    bottomMargin = dip(56)
                    gravity = Gravity.BOTTOM
                }
                backgroundResource = R.drawable.pre_lollipop_elevation_up
            }

            // elevation
            view {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                    topMargin = dip(56)
                }
                backgroundResource = R.drawable.pre_lollipop_elevation
            }
        }
    }

}