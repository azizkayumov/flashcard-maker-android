package com.piapps.flashcardpro.features.labels.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.LabelDb
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-09-27 at 23:34.
 */

class DeleteLabel
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(label: LabelDb) {
        repository.delete(label)
    }
}