package com.piapps.flashcardpro.features.stats

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Description
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.lineChart

/**
 * Created by abduaziz on 2019-10-29 at 13:53.
 */

fun StatsFragment.UI(): View {
    return ctx.frameLayout {
        backgroundColorResource = theme.colorBackground

        actionBar = actionBar {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
            backgroundColorResource = theme.colorPrimary
            tvTitle.textColorResource = theme.colorPrimaryText
            ivControl.setIconColor(ctx, theme.colorIconActive)
            ivMenu.setIconColor(ctx, theme.colorIconActive)

            setTitle(ctx.getLocalizedString(R.string.study))
            onBackClick {
                close()
            }
        }

        verticalLayout {
            layoutParams = FrameLayout.LayoutParams(matchParent, matchParent).apply {
                topMargin = dip(56)
            }

            lineChartAccuracy = lineChart {
                layoutParams = LinearLayout.LayoutParams(matchParent, 0).apply {
                    weight = 1F
                    margin = dip(16)
                }
                backgroundColorResource = theme.colorBackground
                setNoDataTextColor(theme.colorSecondaryText)
                xAxis.granularity = 1F
                axisLeft.axisMaximum = 110F
                axisRight.axisMaximum = 110F
                xAxis.textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                axisRight.textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                axisLeft.textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                legend.textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                description = Description().apply {
                    textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                    text = ctx.getLocalizedString(R.string.based_on_quiz_results)
                }
                setOnChartValueSelectedListener(this@UI)
            }

            lineChartStudy = lineChart {
                layoutParams = LinearLayout.LayoutParams(matchParent, 0).apply {
                    weight = 1F
                    margin = dip(16)
                }
                backgroundColorResource = theme.colorBackground
                setNoDataTextColor(theme.colorSecondaryText)
                xAxis.granularity = 1F
                axisLeft.axisMinimum = 0F
                axisRight.axisMinimum = 0F
                xAxis.textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                axisRight.textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                axisLeft.textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                legend.textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                description = Description().apply {
                    textColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
                    text = ""
                }
                setOnChartValueSelectedListener(this@UI)
            }
        }

        // elevation
        view {
            layoutParams = FrameLayout.LayoutParams(matchParent, dip(2)).apply {
                topMargin = dip(56)
            }
            setBackgroundResource(R.drawable.pre_lollipop_elevation)
        }
    }
}