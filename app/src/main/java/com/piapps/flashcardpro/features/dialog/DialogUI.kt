package com.piapps.flashcardpro.features.dialog

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Created by abduaziz on 2019-10-03 at 21:39.
 */

class DialogUI : AnkoComponent<DialogFragment> {

    override fun createView(ui: AnkoContext<DialogFragment>) = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)

            cardView {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    gravity = Gravity.CENTER
                    horizontalMargin = dip(56)
                }
                radius = dip(8).toFloat()
                setCardBackgroundColor(ContextCompat.getColor(ctx, owner.theme.white))

                verticalLayout {
                    layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)

                    ui.owner.iv = appCompatImageView {
                        layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                            gravity = Gravity.CENTER_HORIZONTAL
                            margin = dip(16)
                        }
                        backgroundResource = R.drawable.circle_gray
                        padding = dip(16)
                        imageResource = R.drawable.ic_check
                        setIconColor(ctx, owner.theme.colorAccent)
                    }

                    ui.owner.tvMessage = textView {
                        layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                            horizontalMargin = dip(16)
                            bottomMargin = dip(8)
                        }
                        gravity = Gravity.CENTER_HORIZONTAL
                        textSize = 14F
                        textColorResource = owner.theme.colorPrimaryText
                        text = ctx.getLocalizedString(R.string.set_name_example)
                    }

                    ui.owner.tvOk = textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                            gravity = Gravity.END
                        }
                        setRippleEffect()
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