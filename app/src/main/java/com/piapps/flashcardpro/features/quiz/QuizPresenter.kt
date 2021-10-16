package com.piapps.flashcardpro.features.quiz

import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.db.tables.Stats
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.core.settings.Settings
import com.piapps.flashcardpro.features.quiz.interactor.*
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

/**
 * Created by abduaziz on 2019-10-29 at 13:15.
 */

class QuizPresenter(var view: QuizView?) : BasePresenter(view) {

    @Inject
    lateinit var getSet: GetSetDetails

    @Inject
    lateinit var getCards: GetCards

    @Inject
    lateinit var saveCard: SaveCard

    @Inject
    lateinit var saveStats: SaveStats

    @Inject
    lateinit var saveSet: SaveSet

    @Inject
    lateinit var settings: Settings

    private var setId = 0L
    var set = SetDb()
    private var answers = arrayListOf<Int>()

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
            it.forEach {
                answers.add(-1)
            }
        }
    }

    fun setCardAnswered(pos: Int, card: CardDb) {
        if (answers[pos] == 1 || answers[pos] == 0) return
        answers[pos] = 1

        card.rating = min(5, card.rating + 1)
        saveCard(card)
    }

    fun setCardNotAnswered(pos: Int, card: CardDb) {
        if (answers[pos] == 1 || answers[pos] == 0) return
        answers[pos] = 0

        card.rating = max(1,card.rating - 1)
        saveCard(card)
    }

    fun saveStats() {
        val stats = Stats(System.currentTimeMillis())

        var accuracy = answers.filter { it == 1 }.size

        stats.accuracy = (100 * accuracy.toFloat() / set.count).toInt()
        if (stats.accuracy > 100) stats.accuracy = 100

        stats.numberOfCards = set.count
        stats.setId = set.id
        stats.studyDuration = set.lastStudyDuration

        saveStats(stats)

        set.lastStudyDuration = 0L
        saveSet(set)
    }
}