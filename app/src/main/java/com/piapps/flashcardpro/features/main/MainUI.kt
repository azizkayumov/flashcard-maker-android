package com.piapps.flashcardpro.features.main

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.kent.layouts.*
import com.kent.layouts.viewgroup.drawerLayout
import com.kent.layouts.viewgroup.frameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-09-22 at 00:53.
 */

fun MainFragment.UI(): View {
    drawerLayout = ctx.drawerLayout {
        layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
        backgroundColorResource = theme.colorBackground
        isClickable = true
        fitsSystemWindows = true

        frameLayout {
            layoutParams = DrawerLayout.LayoutParams(matchParent, matchParent)
            isClickable = true

            actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = theme.colorPrimary
                tvTitle.textColorResource = theme.colorPrimaryText
                ivControl.setIconColor(ctx, theme.colorIconActive)
                ivMenu.setIconColor(ctx, theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.app_name))
                backIcon = R.drawable.ic_navigation_menu
                onBackClick { openDrawer() }
            }

            rv = recyclerView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(56)
                }
                padding = dip(8)
                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                // itemAnimator = null
                bottomPadding = dip(56)
                clipToPadding = false
            }

            ivNightMode = imageView {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.END
                    rightMargin = dip(56)
                }
                padding = dip(16)
                setImageResource(R.drawable.ic_night)
                setIconColor(ctx, theme.colorIconActive)
            }

            tvNothing = textView {
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    margin = dip(56)
                    gravity = Gravity.CENTER
                }
                gravity = Gravity.CENTER
                textSize = 14F
                textColorResource = theme.colorSecondaryText
            }

            fab = floatingActionButton {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.BOTTOM or Gravity.END
                    margin = dip(16)
                }
                backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(ctx, theme.white))
                rippleColor = ContextCompat.getColor(ctx, theme.colorDivider)
                setImageResource(R.drawable.ic_rainbow_icon)
            }

            // elevation
            view {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                    topMargin = dip(56)
                }
                setBackgroundResource(R.drawable.pre_lollipop_elevation)
            }
        }

        navigationView = navigationView {
            layoutParams = DrawerLayout.LayoutParams(wrapContent, matchParent).apply {
                gravity = Gravity.START
            }
            fitsSystemWindows = true
            backgroundColorResource = theme.colorPrimary

            rvNavigation = recyclerView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                layoutManager = LinearLayoutManager(ctx)
                itemAnimator = null
                bottomPadding = dip(56)
                clipToPadding = false
            }
        }
    }
    return drawerLayout
}