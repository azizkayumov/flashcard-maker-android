package com.piapps.flashcardpro.features.main.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.SetDb
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-03 at 20:38.
 */

class PutBackSet
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(setId: Long, onResult: (SetDb) -> Unit = {}) {
        doAsync {
            val set = repository.getSet(setId)
            set?.isTrash = false
            if (set != null) {
                repository.save(set)
                uiThread {
                    onResult(set)
                }
            }
        }
    }
}