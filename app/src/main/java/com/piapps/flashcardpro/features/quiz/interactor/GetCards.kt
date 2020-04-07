package com.piapps.flashcardpro.features.quiz.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.extension.doAsync
import com.piapps.flashcardpro.core.extension.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-30 at 22:54.
 */

class GetCards
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(setId: Long, onResult: (List<CardDb>) -> Unit = {}) {
        doAsync {
            val result = repository.getCardsByRating(setId).shuffled()
            uiThread {
                onResult(result)
            }
        }
    }
}