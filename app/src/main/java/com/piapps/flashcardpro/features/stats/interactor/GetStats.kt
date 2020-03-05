package com.piapps.flashcardpro.features.stats.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.Stats
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.extension.doAsync
import com.piapps.flashcardpro.core.extension.uiThread
import com.piapps.flashcardpro.core.functional.Either
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-31 at 23:04.
 */

class GetStats
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(setId: Long, from: Long, to: Long, onResult: (Either<Failure, List<Stats>>) -> Unit = {}) {
        doAsync {
            val result = repository.getStats(setId, from, to)
            uiThread {
                if (result.isEmpty())
                    onResult(Either.Left(Failure.NoStatsFound))
                else
                    onResult(Either.Right(result))
            }
        }
    }
}