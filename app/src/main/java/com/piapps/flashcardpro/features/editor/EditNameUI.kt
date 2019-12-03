package com.piapps.flashcardpro.features.editor

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.makeBold
import com.piapps.flashcardpro.core.extension.setRippleEffectBorderless
import com.piapps.flashcardpro.core.extension.setStyle
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Created by abduaziz on 2019-10-03 at 21:39.
 */

class EditNameUI : AnkoComponent<EditNameFragment> {

    override fun createView(ui: AnkoContext<EditNameFragment>) = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)

            cardView {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    topMargin = dip(100)
                    horizontalMargin = dip(56)
                }
                radius = dip(8).toFloat()
                setCardBackgroundColor(ContextCompat.getColor(ctx, owner.theme.white))

                verticalLayout {
                    layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)

                    textView {
                        setStyle(ctx, android.R.style.TextAppearance_DeviceDefault_Widget_ActionBar_Title)
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                            margin = dip(16)
                        }
                        gravity = Gravity.CENTER_VERTICAL
                        text = ctx.getLocalizedString(R.string.set_name)
                        textColorResource = owner.theme.colorPrimaryText
                    }

                    textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                            horizontalMargin = dip(16)
                        }
                        textSize = 12F
                        textColorResource = owner.theme.colorSecondaryText
                        text = ctx.getLocalizedString(R.string.set_name_example)
                    }

                    ui.owner.et = editText {
                        layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                            horizontalMargin = dip(16)
                        }
                        textColorResource = owner.theme.colorPrimaryText
                        setHintTextColor(ContextCompat.getColor(ctx, owner.theme.colorSecondaryText))
                        maxLines = 1
                    }

                    ui.owner.tvOk = textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                            gravity = Gravity.END
                        }
                        setRippleEffectBorderless()
                        verticalPadding = dip(16)
                        horizontalPadding = dip(32)
                        textSize = 14F
                        textColorResource = owner.theme.colorAccent
                        makeBold()
                        text = ctx.getLocalizedString(R.string.ok)
                    }
                }
            }
        }
    }

}