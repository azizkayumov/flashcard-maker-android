package com.piapps.flashcardpro.features.editor

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.setIconColor
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-10-09 at 23:56.
 */

class EditCardTextUI : AnkoComponent<EditCardTextFragment> {

    override fun createView(ui: AnkoContext<EditCardTextFragment>) = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = owner.theme.colorBackground

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                onBackClick { ui.owner.close() }
                setTitle(ctx.getLocalizedString(R.string.edit_text))
            }

            ui.owner.ivOk = appCompatImageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.END
                }
                padding = dip(16)
                imageResource = R.drawable.ic_check
                setIconColor(ctx, owner.theme.colorIconActive)
            }

            ui.owner.editText = editText {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(72)
                    horizontalMargin = dip(16)
                }
                hint = ctx.getLocalizedString(R.string.edit_text_hint)
                textColorResource = owner.theme.colorPrimaryText
                setHintTextColor(ContextCompat.getColor(ctx, owner.theme.colorSecondaryText))
                gravity = Gravity.TOP
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