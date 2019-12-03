package com.piapps.flashcardpro.features.main.adapter.cells

import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.ellipsize
import com.piapps.flashcardpro.core.extension.makeBold
import com.piapps.flashcardpro.core.extension.setRippleEffect
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Created by abduaziz on 2019-09-25 at 23:39.
 */
class SetItemUI : AnkoComponent<ViewGroup> {

    companion object {
        val parentId = 9
        val tvTitleId = 10
        val tvCountId = 11
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
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