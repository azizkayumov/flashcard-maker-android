package com.piapps.flashcardpro.features.editor.adapter.cells

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appTheme
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-10-26 at 19:33.
 */

class LabelUI : AnkoComponent<ViewGroup> {

    companion object {
        val tvId = 44
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        frameLayout {
            lparams(wrapContent, wrapContent)
            verticalPadding = dip(8)
            horizontalPadding = dip(4)

            textView {
                id = tvId
                layoutParams = FrameLayout.LayoutParams(wrapContent, dip(24))
                backgroundResource = R.drawable.tag
                gravity = Gravity.CENTER
                textSize = 12F
                textColorResource = R.color.white
                horizontalPadding = dip(4)
            }
        }
    }

}