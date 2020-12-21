package com.piapps.flashcardpro.features.main.interactor

import com.piapps.flashcardpro.BuildConfig
import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.extension.doAsync
import com.piapps.flashcardpro.core.extension.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-09-27 at 23:16.
 */

class GetAllSets
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(onResult: (List<SetDb>) -> Unit = {}) {
        doAsync {
            val result = repository.getAllSets()
            result.forEach {
                if (it.order < Int.MAX_VALUE){
                    it.order = it.id
                }
            }
            uiThread {
                onResult(result)
            }
        }
    }
}