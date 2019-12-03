package com.piapps.flashcardpro.features.editor.adapter.cells

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.setIconColor
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-10-14 at 22:28.
 */

class PhotosItemUI : AnkoComponent<ViewGroup> {
    companion object {
        var ivMainId = 1
        var ivSelectorId = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        relativeLayout {
            layoutParams = RelativeLayout.LayoutParams(matchParent, dip(128))
            appCompatImageView {
                layoutParams = RelativeLayout.LayoutParams(matchParent, matchParent)
                id = ivMainId
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            appCompatImageView {
                layoutParams = RelativeLayout.LayoutParams(dip(40), dip(40)).apply {
                    alignParentTop()
                    alignParentRight()
                }
                padding = dip(8)
                imageResource = R.drawable.circle_white
            }

            appCompatImageView {
                layoutParams = RelativeLayout.LayoutParams(dip(40), dip(40)).apply {
                    alignParentTop()
                    alignParentRight()
                }
                id = ivSelectorId
                setIconColor(ctx, ctx.appTheme().colorAccent)
                padding = dip(8)
            }
        }
    }
}