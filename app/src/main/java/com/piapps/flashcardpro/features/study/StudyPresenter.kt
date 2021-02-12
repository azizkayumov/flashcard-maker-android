package com.piapps.flashcardpro.features.study

import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.core.settings.Settings
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

    @Inject
    lateinit var settings: Settings

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
        view?.setColors(if (set.color.isNotBlank()) set.color else settings.getDefaultCardBackgroundColor(),
            if (!set.textColor.isNullOrBlank()) set.textColor!! else settings.getDefaultCardTextColor())
    }

    fun loadCards() {
        getCards(set.id) {
            view?.showCards(it)
        }
        studyStart = System.currentTimeMillis()
    }

    fun random(from: Int, until: Int, notEqual: Int): Int {
        if (until - from <= 1) return from
        var random = Random.nextInt(from, until)
        while (random == notEqual)
            random = Random.nextInt(from, until)
        return random
    }

}