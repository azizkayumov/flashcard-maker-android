package com.piapps.flashcardpro.features.editor.adapter.cells

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appTheme

/**
 * Created by abduaziz on 2019-10-14 at 22:28.
 */

class PhotosItemUI {
    companion object {
        var ivMainId = 1
        var ivSelectorId = 2
    }

    fun createView(ctx: Context): View = with(ctx) {
        frameLayout {
            lparams(matchParent, dip(128))

            imageView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                id = ivMainId
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            imageView {
                layoutParams = FrameLayout.LayoutParams(dip(40), dip(40)).apply {
                    gravity = Gravity.TOP or Gravity.END
                }
                padding = dip(8)
                setImageResource(R.drawable.circle_white)
            }

            imageView {
                layoutParams = FrameLayout.LayoutParams(dip(40), dip(40)).apply {
                    gravity = Gravity.TOP or Gravity.END
                }
                id = ivSelectorId
                setIconColor(ctx, ctx.appTheme().colorAccent)
                padding = dip(8)
            }
        }
    }
}