package com.piapps.flashcardpro.features.quiz.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.CardDb
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-03 at 20:16.
 */

class SaveCard
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(card: CardDb) {
        repository.save(card)
    }
}