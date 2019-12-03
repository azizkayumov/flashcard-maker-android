package com.piapps.flashcardpro.features.editor

import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.setIconColor
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by abduaziz on 2019-10-14 at 22:47.
 */

class PhotosUI : AnkoComponent<PhotosFragment> {

    override fun createView(ui: AnkoContext<PhotosFragment>): View = with(ui) {
        frameLayout {
            backgroundResource = owner.theme.white
            isClickable = true

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.add_image))
            }

            ui.owner.recyclerView = recyclerView {
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
                backgroundResource = R.drawable.pre_lollipop_elevation
            }

            ui.owner.ivOk = appCompatImageView {
                imageResource = R.drawable.ic_check
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    margin = dip(16)
                    gravity = Gravity.END
                }
                setIconColor(ctx, owner.theme.colorIconActive)
            }
        }
    }
}