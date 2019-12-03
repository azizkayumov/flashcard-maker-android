package com.piapps.flashcardpro.features.quiz.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.SetDb
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-03 at 20:38.
 */

class SaveSet
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(set: SetDb) {
        repository.save(set)
    }
}