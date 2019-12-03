package com.piapps.flashcardpro.features.stats

import com.piapps.flashcardpro.core.db.tables.Stats
import com.piapps.flashcardpro.core.platform.BaseView

/**
 * Created by abduaziz on 2019-10-29 at 13:55.
 */

interface StatsView : BaseView {
    fun setTitle(s: String)
    fun showStats(list: List<Stats>)
}