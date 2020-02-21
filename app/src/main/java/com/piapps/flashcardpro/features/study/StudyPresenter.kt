package com.piapps.flashcardpro.features.study

import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.features.study.interactor.GetCards
import com.piapps.flashcardpro.features.study.interactor.GetSetDetails
import com.piapps.flashcardpro.features.study.interactor.SaveSet
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by abduaziz on 2019-10-29 at 13:06.
 */

class StudyPresenter(var view: StudyView?) : BasePresenter(view) {

    @Inject
    lateinit var getSet: GetSetDetails

    @Inject
    lateinit var getCards: GetCards

    @Inject
    lateinit var saveSet: SaveSet

    private var setId = 0L
    var set = SetDb()
    var studyStart = 0L

    fun loadSetDetails(id: Long) {
        this.setId = id
        getSet(setId) {
            it.callEither(::handleFailure, ::showSetDetails)
        }
    }

    fun showSetDetails(set: SetDb) {
        this.set = set
        if (set.color.isNotBlank())
            view?.setSetColor(set.color)
    }

    fun loadCards() {
        getCards(set.id) {
            view?.showCards(it)
        }
        studyStart = System.currentTimeMillis()
    }

    fun random(until: Int, notEqual: Int): Int {
        if (until == 1) return 0 // BUG FIX: App not responding
        var random = Random.nextInt(0, until)
        while (random == notEqual)
            random = Random.nextInt(0, until)
        return random
    }

}