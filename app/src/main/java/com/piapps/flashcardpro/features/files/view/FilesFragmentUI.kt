package com.piapps.flashcardpro.features.files.view

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.setIconColor
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView

class FilesFragmentUI : AnkoComponent<FilesFragment> {
    override fun createView(ui: AnkoContext<FilesFragment>): View = with(ui) {
        frameLayout {
            backgroundResource = owner.theme.colorBackground
            isClickable = true

            ui.owner.recyclerView = recyclerView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(56)
                }
            }

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)
            }

            view {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                    topMargin = dip(56)
                }
                backgroundResource = R.drawable.pre_lollipop_elevation
            }

            ui.owner.fab = floatingActionButton {
                imageResource = R.drawable.ic_check
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    margin = dip(16)
                    gravity = Gravity.BOTTOM or Gravity.END
                }
                hide()
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, owner.theme.colorAccent))
                rippleColor = ContextCompat.getColor(ui.ctx, owner.theme.colorDivider)
                setIconColor(ctx, owner.theme.white)
            }
        }
    }
}