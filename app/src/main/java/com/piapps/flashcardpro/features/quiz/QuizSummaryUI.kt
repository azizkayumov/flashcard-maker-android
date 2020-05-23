package com.piapps.flashcardpro.features.quiz

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-10-03 at 21:39.
 */

fun QuizSummaryFragment.UI(): View {
    return ctx.frameLayout {
        lparams(matchParent, matchParent)

        cardView {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                topMargin = dip(100)
                leftMargin = dip(56)
                rightMargin = dip(56)
            }
            radius = dip(8).toFloat()
            setCardBackgroundColor(ContextCompat.getColor(ctx, theme.white))

            verticalLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)

                horizontalLayout {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                        margin = dip(16)
                    }
                    gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL

                    tvAccuracy = textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
                        text = "75"
                        textSize = 56F
                        textColorResource = R.color.colorGoodGrade
                    }

//                    textView {
//                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
//                        text = "%"
//                        textSize = 15F
//                        textColorResource = theme.colorPrimaryText
//                    }
                }

                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    gravity = Gravity.CENTER
                    textSize = 14F
                    textColorResource = theme.colorPrimaryText
                    text = ctx.getLocalizedString(R.string.quiz_results)
                }

                tvCreateHint = textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                        topMargin = dip(16)
                    }
                    textSize = 14F
                    textColorResource = theme.colorPrimaryText
                    text = ctx.getLocalizedString(R.string.create_new_set_with_weak_cards)
                }

                horizontalLayout {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                    gravity = Gravity.END

                    tvNo = textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
                        setRippleEffect()
                        verticalPadding = dip(16)
                        horizontalPadding = dip(32)
                        textSize = 14F
                        textColorResource = theme.colorAccent
                        makeBold()
                        text = ctx.getLocalizedString(R.string.cancel)
                    }

                    tvYes = textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
                        setRippleEffect()
                        verticalPadding = dip(16)
                        horizontalPadding = dip(32)
                        textSize = 14F
                        textColorResource = theme.colorAccent
                        makeBold()
                        text = ctx.getLocalizedString(R.string.ok)
                    }
                }
            }
        }
    }
}
