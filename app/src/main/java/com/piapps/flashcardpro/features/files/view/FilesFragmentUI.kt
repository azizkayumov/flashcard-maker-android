package com.piapps.flashcardpro.features.files.view

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar

fun FilesFragment.UI(): View {
    return ctx.frameLayout {
        backgroundColorResource = theme.colorBackground
        isClickable = true

        recyclerView = recyclerView {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(56)
            }
        }

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)
        }

        view {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                topMargin = dip(56)
            }
            setBackgroundResource(R.drawable.pre_lollipop_elevation)
        }

        fab = floatingActionButton {
            setImageResource(R.drawable.ic_check)
            layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                margin = dip(16)
                gravity = Gravity.BOTTOM or Gravity.END
            }
            hide()
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, theme.colorAccent))
            rippleColor = ContextCompat.getColor(ctx, theme.colorDivider)
            setIconColor(ctx, theme.white)
        }
    }
}