package com.piapps.flashcardpro.features.stats

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.kent.layouts.dip
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.Stats
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.longLog
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.util.DateUtils
import kotlin.math.ceil

/**
 * Created by abduaziz on 2019-10-29 at 13:52.
 */

class StatsFragment : BaseFragment(), StatsView, OnChartValueSelectedListener {

    companion object {
        fun set(setId: Long): StatsFragment {
            return StatsFragment().apply {
                arguments = Bundle().apply {
                    putLong("id", setId)
                }
            }
        }
    }

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        canSwipe = false
        super.create()
    }

    lateinit var presenter: StatsPresenter

    lateinit var lineChartAccuracy: LineChart
    lateinit var lineChartStudy: LineChart

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = StatsPresenter(this)
        appComponent.inject(presenter)

        val id = args?.getLong("id", 1L) ?: 1L
        presenter.loadSet(id)
    }

    override fun resume() {
        presenter.loadStats()
    }

    override fun setTitle(s: String) {
        actionBar?.setTitle(s)
    }

    override fun showStats(list: List<Stats>) {
        val dates = arrayListOf<String>()
        val accuracyEntries = arrayListOf<Entry>()
        val numbersEntries = arrayListOf<Entry>()
        val studyTimeEntries = arrayListOf<Entry>()
        list.forEachIndexed { index, stats ->
            accuracyEntries.add(Entry(index.toFloat(), stats.accuracy.toFloat()))
            numbersEntries.add(Entry(index.toFloat(), stats.numberOfCards.toFloat()))
            studyTimeEntries.add(Entry(index.toFloat(), ceil(stats.studyDuration / 60000F)))
            dates.add(DateUtils.getMonthDay(stats.id))
        }

        val valueFormatter = object : IndexAxisValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                if (value == -1F) return return ctx.getLocalizedString(R.string.past)
                if (value >= dates.size) return ctx.getLocalizedString(R.string.future)
                return dates[value.toInt()]
            }
        }
        if (list.size > 10) {
            lineChartAccuracy.zoom(list.size / 10F, 1F, lineChartAccuracy.width.toFloat() - ctx.dip(32), 0F)
            lineChartStudy.zoom(list.size / 10F, 1F, lineChartAccuracy.width.toFloat() - ctx.dip(32), 0F)
        }

        val accuracyData = LineDataSet(accuracyEntries, ctx.getLocalizedString(R.string.accuracy))
        accuracyData.setDrawFilled(true)
        accuracyData.mode = LineDataSet.Mode.CUBIC_BEZIER
        accuracyData.valueTextColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
        accuracyData.colors = listOf(ContextCompat.getColor(ctx, R.color.md_green_500))
        accuracyData.fillColor = ContextCompat.getColor(ctx, R.color.md_green_500)
        accuracyData.circleColors = listOf(ContextCompat.getColor(ctx, R.color.md_green_500))


        val numberOfCardsData = LineDataSet(numbersEntries, ctx.getLocalizedString(R.string.number_of_cards))
        numberOfCardsData.setDrawFilled(false)
        numberOfCardsData.mode = LineDataSet.Mode.CUBIC_BEZIER
        numberOfCardsData.valueTextColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
        numberOfCardsData.colors = listOf(ContextCompat.getColor(ctx, R.color.colorAccent))
        numberOfCardsData.circleColors = listOf(ContextCompat.getColor(ctx, R.color.colorAccent))
        numberOfCardsData.fillColor = ContextCompat.getColor(ctx, R.color.colorAccent)

        lineChartAccuracy.xAxis.valueFormatter = valueFormatter
        lineChartAccuracy.data = LineData(numberOfCardsData, accuracyData)
        lineChartAccuracy.animateY(1500)

        val studyTimeData = LineDataSet(studyTimeEntries, ctx.getLocalizedString(R.string.study_duration_for_quiz))
        studyTimeData.setDrawFilled(true)
        studyTimeData.mode = LineDataSet.Mode.CUBIC_BEZIER
        studyTimeData.valueTextColor = ContextCompat.getColor(ctx, theme.colorPrimaryText)
        studyTimeData.colors = listOf(ContextCompat.getColor(ctx, R.color.md_blue_500))
        studyTimeData.circleColors = listOf(ContextCompat.getColor(ctx, R.color.md_blue_500))
        studyTimeData.fillColor = ContextCompat.getColor(ctx, R.color.md_blue_500)

        lineChartStudy.xAxis.valueFormatter = valueFormatter
        lineChartStudy.data = LineData(studyTimeData)
        lineChartStudy.animateY(1500)
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        longLog("onValueSelected: ${e?.x}, ${e?.y}")
    }

    override fun onNothingSelected() {

    }

    override fun showToast(res: Int) {
        toast(res)
    }

}