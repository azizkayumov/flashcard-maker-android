package com.piapps.flashcardpro.features.editor.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.CardDb
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-03 at 23:56.
 */

class GetCards
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(setId: Long, onResult: (List<CardDb>) -> Unit = {}) {
        doAsync {
            val result = repository.getCards(setId)
            uiThread {
                onResult(result)
            }
        }
    }
}