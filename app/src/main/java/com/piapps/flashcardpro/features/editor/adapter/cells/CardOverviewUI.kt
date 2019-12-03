package com.piapps.flashcardpro.features.editor.adapter.cells

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.autoResizeTextView
import com.piapps.flashcardpro.core.extension.setRippleEffect
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Created by abduaziz on 2019-09-25 at 23:39.
 */
class CardOverviewUI : AnkoComponent<ViewGroup> {

    companion object {
        val parentId = 9
        val tvFrontId = 10
        val ivFrontId = 11
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        frameLayout {
            lparams(matchParent, wrapContent)

            cardView {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(128)).apply {
                    margin = dip(4)
                }
                setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.c1))
                radius = dip(4).toFloat()

                view {
                    id = parentId
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    padding = dip(8)
                    setRippleEffect()
                }

                appCompatImageView {
                    id = ivFrontId
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                }

                autoResizeTextView {
                    id = tvFrontId
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                    textColorResource = R.color.colorPrimaryText
                    padding = dip(4)
                    gravity = Gravity.CENTER
                }
            }
        }
    }
}