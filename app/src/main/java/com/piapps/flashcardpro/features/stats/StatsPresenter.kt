package com.piapps.flashcardpro.features.stats

import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.db.tables.Stats
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.features.stats.interactor.GetSetDetails
import com.piapps.flashcardpro.features.stats.interactor.GetStats
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-29 at 13:56.
 */

class StatsPresenter(var view: StatsView?) : BasePresenter(view) {

    @Inject
    lateinit var getSet: GetSetDetails

    @Inject
    lateinit var getStats: GetStats

    var set = SetDb()
    var setId = 1L
    fun loadSet(setId: Long) {
        this.setId = setId
        getSet(setId) {
            it.callEither(::handleFailure, ::handleSet)
        }
    }

    fun handleSet(set: SetDb) {
        this.set = set
        view?.setTitle(set.title)
    }

    fun loadStats() {
        val to = System.currentTimeMillis()
        val from = to - 2592000000
        getStats(setId, from, to) {
            it.callEither(::handleFailure, ::handleStats)
        }
    }

    fun handleStats(list: List<Stats>) {
        view?.showStats(list)
    }

}