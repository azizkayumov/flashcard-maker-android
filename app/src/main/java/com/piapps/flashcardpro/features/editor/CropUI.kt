package com.piapps.flashcardpro.features.editor

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.FrameLayout
import com.isseiaoki.simplecropview.CropImageView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-11-17 at 00:56.
 */

class CropUI : AnkoComponent<CropFragment> {

    override fun createView(ui: AnkoContext<CropFragment>) = with(ui) {
        frameLayout {
            backgroundColorResource = owner.theme.colorBackground

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.crop_image))
                onBackClick {
                    ui.owner.close()
                }
            }

            ui.owner.cropView = cropImageView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(56)
                    bottomMargin = dip(56)
                }
                padding = dip(24)
                setFrameColor(ContextCompat.getColor(ctx, owner.theme.colorAccent))
                setFrameStrokeWeightInDp(1)

                setGuideColor(ContextCompat.getColor(ctx, owner.theme.colorAccent))
                setGuideShowMode(CropImageView.ShowMode.SHOW_ON_TOUCH)
                setGuideStrokeWeightInDp(1)

                setHandleColor(ContextCompat.getColor(ctx, owner.theme.colorAccent))
                setHandleShowMode(CropImageView.ShowMode.SHOW_ALWAYS)
                setHandleSizeInDp(14)
                setMinFrameSizeInDp(50)
                setOverlayColor(ContextCompat.getColor(ctx, R.color.cropOverlay))
                setTouchPaddingInDp(8)

                setCropMode(CropImageView.CropMode.FREE)
            }

            ui.owner.ivDone = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.RIGHT
                }
                padding = dip(16)
                imageResource = R.drawable.ic_check
                setIconColor(ctx, owner.theme.colorAccent)
            }

            ui.owner.ivLeft = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    padding = dip(16)
                    gravity = Gravity.BOTTOM
                }
                setRippleEffectBorderless()
                imageResource = R.drawable.ic_rotate_left
                setIconColor(ctx, owner.theme.colorIconActive)
            }

            ui.owner.ivRight = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    padding = dip(16)
                    gravity = Gravity.BOTTOM or Gravity.END
                }
                setRippleEffectBorderless()
                imageResource = R.drawable.ic_rotate_right
                setIconColor(ctx, owner.theme.colorIconActive)
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