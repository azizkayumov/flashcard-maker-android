package com.piapps.flashcardpro.features.settings

import android.view.View
import android.widget.FrameLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-10-27 at 00:12.
 */

fun SettingsFragment.UI(): View {
    return ctx.frameLayout {
        backgroundColorResource = theme.colorBackground

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)

            setTitle(ctx.getLocalizedString(R.string.settings))
            onBackClick {
                close()
            }
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