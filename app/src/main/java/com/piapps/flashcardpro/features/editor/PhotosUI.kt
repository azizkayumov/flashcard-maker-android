package com.piapps.flashcardpro.features.editor

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-10-14 at 22:47.
 */

fun PhotosFragment.UI(): View {
    return ctx.frameLayout {
        backgroundColorResource = theme.white
        isClickable = true

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)

            setTitle(ctx.getLocalizedString(R.string.add_image))
        }

        recyclerView = recyclerView {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(56)
            }
            layoutManager = GridLayoutManager(ctx, 2)
            itemAnimator = null
        }

        view {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                topMargin = dip(56)
            }
            setBackgroundResource(R.drawable.pre_lollipop_elevation)
        }

        ivOk = imageView {
            setImageResource(R.drawable.ic_check)
            layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                margin = dip(16)
                gravity = Gravity.END
            }
            setIconColor(ctx, theme.colorIconActive)
        }
    }
}