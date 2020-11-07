package com.piapps.flashcardpro.features.editor.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.CardDb
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-06 at 17:20.
 */

class DeleteCards
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(cards: List<CardDb>) {
        repository.deleteCards(cards)
    }
}