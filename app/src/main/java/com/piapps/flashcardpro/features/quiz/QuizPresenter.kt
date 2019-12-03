package com.piapps.flashcardpro.features.quiz

import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.db.tables.Stats
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.features.quiz.interactor.*
import javax.inject.Inject

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
        if (set.color.isNotBlank())
            view?.setSetColor(set.color)
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

        card.rating = card.rating + 1
        if (card.rating > 5) card.rating = 5
        saveCard(card)
    }

    fun setCardNotAnswered(pos: Int, card: CardDb) {
        if (answers[pos] == 1 || answers[pos] == 0) return
        answers[pos] = 0

        card.rating = card.rating - 1
        if (card.rating < -5) card.rating = -5
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