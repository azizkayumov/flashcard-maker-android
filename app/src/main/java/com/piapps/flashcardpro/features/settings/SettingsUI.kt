package com.piapps.flashcardpro.features.settings

import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.setIconColor
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-10-27 at 00:12.
 */

class SettingsUI : AnkoComponent<SettingsFragment> {

    override fun createView(ui: AnkoContext<SettingsFragment>) = with(ui) {
        frameLayout {
            backgroundColorResource = owner.theme.colorBackground

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.settings))
                onBackClick {
                    ui.owner.close()
                }
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