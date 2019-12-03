package com.piapps.flashcardpro.features.main

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.setIconColor
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.drawerLayout

/**
 * Created by abduaziz on 2019-09-22 at 00:53.
 */

class MainUI : AnkoComponent<MainFragment> {

    override fun createView(ui: AnkoContext<MainFragment>): View {
        with(ui) {
            ui.owner.drawerLayout = drawerLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
                backgroundColorResource = owner.theme.colorBackground
                isClickable = true
                fitsSystemWindows = true

                frameLayout {
                    layoutParams = DrawerLayout.LayoutParams(matchParent, matchParent)
                    isClickable = true

                    ui.owner.actionBar = actionBar {
                        layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                        backgroundColorResource = owner.theme.colorPrimary
                        tvTitle.textColorResource = owner.theme.colorPrimaryText
                        ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                        ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                        setTitle(ctx.getLocalizedString(R.string.app_name))
                        backIcon = R.drawable.ic_navigation_menu
                        onBackClick { owner.openDrawer() }
                    }

                    ui.owner.rv = recyclerView {
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                            topMargin = dip(56)
                        }
                        padding = dip(8)
                        layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                        itemAnimator = null
                        bottomPadding = dip(56)
                        clipToPadding = false
                    }

                    ui.owner.ivNightMode = appCompatImageView {
                        layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                            gravity = Gravity.END
                            rightMargin = dip(56)
                        }
                        padding = dip(16)
                        imageResource = R.drawable.ic_night
                        setIconColor(ctx, owner.theme.colorIconActive)
                    }

                    ui.owner.tvNothing = textView {
                        layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                            margin = dip(56)
                            gravity = Gravity.CENTER
                        }
                        gravity = Gravity.CENTER
                        textSize = 14F
                        textColorResource = owner.theme.colorSecondaryText
                    }

                    ui.owner.fab = floatingActionButton {
                        layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                            gravity = Gravity.BOTTOM or Gravity.END
                            margin = dip(16)
                        }
                        backgroundTintList =
                            ColorStateList.valueOf(ContextCompat.getColor(ctx, owner.theme.colorAccent))
                        rippleColor = ContextCompat.getColor(ui.ctx, owner.theme.colorDivider)
                        imageResource = R.drawable.ic_add
                        setIconColor(ctx, owner.theme.white)
                    }

                    // elevation
                    view {
                        layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                            topMargin = dip(56)
                        }
                        backgroundResource = R.drawable.pre_lollipop_elevation
                    }
                }

                ui.owner.navigationView = navigationView {
                    layoutParams = DrawerLayout.LayoutParams(wrapContent, matchParent).apply {
                        gravity = Gravity.START
                    }
                    fitsSystemWindows = true
                    backgroundColorResource = owner.theme.colorPrimary

                    ui.owner.rvNavigation = recyclerView {
                        layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                        layoutManager = LinearLayoutManager(ctx)
                        itemAnimator = null
                        bottomPadding = dip(56)
                        clipToPadding = false
                    }
                }
            }
        }
        return ui.owner.drawerLayout
    }

}