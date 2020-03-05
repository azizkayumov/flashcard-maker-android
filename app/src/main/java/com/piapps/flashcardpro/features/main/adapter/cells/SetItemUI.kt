package com.piapps.flashcardpro.features.main.adapter.cells

import android.content.Context
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.R

/**
 * Created by abduaziz on 2019-09-25 at 23:39.
 */
class SetItemUI {

    companion object {
        val parentId = 9
        val tvTitleId = 10
        val tvCountId = 11
    }

    fun createView(ctx: Context) = with(ctx) {
        frameLayout {
            lparams(matchParent, wrapContent)

            cardView {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    margin = dip(8)
                }
                minimumHeight = dip(80)
                setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.c1))
                radius = dip(4).toFloat()

                verticalLayout {
                    id = parentId
                    layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)
                    padding = dip(8)
                    setRippleEffect()

                    textView {
                        id = tvTitleId
                        layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)
                        rightPadding = dip(16)
                        textSize = 16F
                        makeBold()
                        textColorResource = R.color.colorPrimaryText
                        lines = 3
                        ellipsize(3)
                    }

                    textView {
                        id = tvCountId
                        layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)
                        textSize = 12F
                        textColorResource = R.color.colorSecondaryText
                    }
                }
            }
        }
    }
}