package com.piapps.flashcardpro.features.quiz.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.Stats
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-03 at 20:16.
 */

class SaveStats
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(stats: Stats) {
        repository.save(stats)
    }
}