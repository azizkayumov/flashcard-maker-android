package com.piapps.flashcardpro.features.labels

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by abduaziz on 2019-10-26 at 21:01.
 */

class LabelsUI : AnkoComponent<LabelsFragment> {

    override fun createView(ui: AnkoContext<LabelsFragment>): View = with(ui) {
        frameLayout {
            backgroundResource = owner.theme.white
            isClickable = true

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.labels))
                onBackClick { ui.owner.close() }
            }

            ui.owner.recyclerView = recyclerView {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(56)
                }
                layoutManager = LinearLayoutManager(ctx)
                itemAnimator = null
                adapter = ui.owner.adapter
            }

            view {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                    topMargin = dip(56)
                }
                backgroundResource = R.drawable.pre_lollipop_elevation
            }

            ui.owner.ivOk = appCompatImageView {
                imageResource = R.drawable.ic_check
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.END
                }
                padding = dip(16)
                setRippleEffect()
                setIconColor(ctx, owner.theme.colorIconActive)
            }

            ui.owner.fab = floatingActionButton {
                layoutParams = FrameLayout.LayoutParams(dip(56), dip(56)).apply {
                    gravity = Gravity.BOTTOM or Gravity.END
                    margin = dip(16)
                }
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, owner.theme.colorAccent))
                rippleColor = ContextCompat.getColor(ui.ctx, owner.theme.colorDivider)
                imageResource = R.drawable.ic_add
                setIconColor(ctx, owner.theme.white)
            }
        }
    }

}