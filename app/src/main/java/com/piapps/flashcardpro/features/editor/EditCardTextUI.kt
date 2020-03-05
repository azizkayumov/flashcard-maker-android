package com.piapps.flashcardpro.features.editor

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-10-09 at 23:56.
 */

fun EditCardTextFragment.UI(): View {
    return ctx.frameLayout {
        lparams(matchParent, matchParent)
        backgroundColorResource = theme.colorBackground

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)

            onBackClick { close() }
            setTitle(ctx.getLocalizedString(R.string.edit_text))
        }

        ivOk = imageView {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.END
            }
            padding = dip(16)
            setImageResource(R.drawable.ic_check)
            setIconColor(ctx, theme.colorIconActive)
        }

        editText = editText {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(72)
                leftMargin = dip(16)
                rightMargin = dip(16)
            }
            hint = ctx.getLocalizedString(R.string.edit_text_hint)
            textColorResource = theme.colorPrimaryText
            setHintTextColor(ContextCompat.getColor(ctx, theme.colorSecondaryText))
            gravity = Gravity.TOP
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