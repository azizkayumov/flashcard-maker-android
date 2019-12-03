package com.piapps.flashcardpro.features.editor.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.functional.Either
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-03 at 20:16.
 */

class GetSetDetails
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(id: Long, onResult: (Either<Failure, SetDb>) -> Unit) {
        val set = repository.getSet(id)
        if (set != null) {
            onResult(Either.Right(set))
        } else {
            onResult(Either.Left(Failure.SetNotFound))
        }
    }
}