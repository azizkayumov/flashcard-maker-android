package com.piapps.flashcardpro.features.stats

import android.support.v4.content.ContextCompat
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.github.mikephil.charting.components.Description
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.lineChart
import com.piapps.flashcardpro.core.extension.setIconColor
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-10-29 at 13:53.
 */

class StatsUI : AnkoComponent<StatsFragment> {

    override fun createView(ui: AnkoContext<StatsFragment>) = with(ui) {
        frameLayout {
            backgroundColorResource = owner.theme.colorBackground

            ui.owner.actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = owner.theme.colorPrimary
                tvTitle.textColorResource = owner.theme.colorPrimaryText
                ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.study))
                onBackClick {
                    ui.owner.close()
                }
            }

            verticalLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                    topMargin = dip(56)
                }

                ui.owner.lineChartAccuracy = lineChart {
                    layoutParams = LinearLayout.LayoutParams(matchParent, 0).apply {
                        weight = 1F
                        margin = dip(16)
                    }
                    backgroundColorResource = owner.theme.colorBackground
                    setNoDataTextColor(owner.theme.colorSecondaryText)
                    xAxis.granularity = 1F
                    axisLeft.axisMaximum = 110F
                    axisRight.axisMaximum = 110F
                    xAxis.textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                    axisRight.textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                    axisLeft.textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                    legend.textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                    description = Description().apply {
                        textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                        text = ctx.getLocalizedString(R.string.based_on_quiz_results)
                    }
                    setOnChartValueSelectedListener(ui.owner)
                }

                ui.owner.lineChartStudy = lineChart {
                    layoutParams = LinearLayout.LayoutParams(matchParent, 0).apply {
                        weight = 1F
                        margin = dip(16)
                    }
                    backgroundColorResource = owner.theme.colorBackground
                    setNoDataTextColor(owner.theme.colorSecondaryText)
                    xAxis.granularity = 1F
                    axisLeft.axisMinimum = 0F
                    axisRight.axisMinimum = 0F
                    xAxis.textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                    axisRight.textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                    axisLeft.textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                    legend.textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                    description = Description().apply {
                        textColor = ContextCompat.getColor(ctx, owner.theme.colorPrimaryText)
                        text = ""
                    }
                    setOnChartValueSelectedListener(ui.owner)
                }
            }

            // elevation
            view {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                    topMargin = dip(56)
                }
                backgroundResource = R.drawable.pre_lollipop_elevation
            }
        }
    }

}