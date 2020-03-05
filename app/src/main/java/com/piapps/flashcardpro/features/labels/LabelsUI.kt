package com.piapps.flashcardpro.features.labels

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-10-26 at 21:01.
 */

fun LabelsFragment.UI(): View {
    return ctx.frameLayout {
        setBackgroundResource(theme.white)
        isClickable = true

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)

            setTitle(ctx.getLocalizedString(R.string.labels))
            onBackClick { close() }
        }

        recyclerView = recyclerView {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(56)
            }
            layoutManager = LinearLayoutManager(ctx)
            itemAnimator = null
            adapter = this@UI.adapter
        }

        view {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                topMargin = dip(56)
            }
            setBackgroundResource(R.drawable.pre_lollipop_elevation)
        }

        ivOk = imageView {
            setImageResource(R.drawable.ic_check)
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.END
            }
            padding = dip(16)
            setRippleEffect()
            setIconColor(ctx, theme.colorIconActive)
        }

        fab = floatingActionButton {
            layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                gravity = Gravity.BOTTOM or Gravity.END
                margin = dip(16)
            }
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, theme.colorAccent))
            rippleColor = ContextCompat.getColor(ctx, theme.colorDivider)
            setImageResource(R.drawable.ic_add)
            setIconColor(ctx, theme.white)
        }
    }
}