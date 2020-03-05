package com.piapps.flashcardpro.features.editor

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.isseiaoki.simplecropview.CropImageView
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.cropImageView
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-11-17 at 00:56.
 */

fun CropFragment.UI(): View {
    return ctx.frameLayout {
        backgroundColorResource = theme.colorBackground

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)

            setTitle(ctx.getLocalizedString(R.string.crop_image))
            onBackClick {
                close()
            }
        }

        cropView = cropImageView {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(56)
                bottomMargin = dip(56)
            }
            padding = dip(24)
            setFrameColor(ContextCompat.getColor(ctx, theme.colorAccent))
            setFrameStrokeWeightInDp(1)

            setGuideColor(ContextCompat.getColor(ctx, theme.colorAccent))
            setGuideShowMode(CropImageView.ShowMode.SHOW_ON_TOUCH)
            setGuideStrokeWeightInDp(1)

            setHandleColor(ContextCompat.getColor(ctx, theme.colorAccent))
            setHandleShowMode(CropImageView.ShowMode.SHOW_ALWAYS)
            setHandleSizeInDp(14)
            setMinFrameSizeInDp(50)
            setOverlayColor(ContextCompat.getColor(ctx, R.color.cropOverlay))
            setTouchPaddingInDp(8)

            setCropMode(CropImageView.CropMode.FREE)
        }

        ivDone = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.RIGHT
            }
            padding = dip(16)
            setImageResource(R.drawable.ic_check)
            setIconColor(ctx, theme.colorAccent)
        }

        ivLeft = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                padding = dip(16)
                gravity = Gravity.BOTTOM
            }
            setRippleEffectBorderless()
            setImageResource(R.drawable.ic_rotate_left)
            setIconColor(ctx, theme.colorIconActive)
        }

        ivRight = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                padding = dip(16)
                gravity = Gravity.BOTTOM or Gravity.END
            }
            setRippleEffectBorderless()
            setImageResource(R.drawable.ic_rotate_right)
            setIconColor(ctx, theme.colorIconActive)
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