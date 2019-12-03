package com.piapps.flashcardpro.features.labels.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.LabelDb
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.functional.Either
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-09-27 at 23:34.
 */

class SaveLabel
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(label: LabelDb, onResult: (Either<Failure, LabelDb>) -> Unit = {}) {
        doAsync {
            val result = repository.save(label)
            uiThread {
                onResult(result)
            }
        }
    }
}