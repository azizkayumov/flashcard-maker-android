package com.piapps.flashcardpro.features.main.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.extension.doAsync
import com.piapps.flashcardpro.core.extension.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-03 at 20:38.
 */

class EmptyTrash
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(onResult: () -> Unit = {}) {
        doAsync {
            val trash = repository.getTrashSets()
            repository.deleteSets(trash)
            uiThread {
                onResult()
            }
        }
    }
}