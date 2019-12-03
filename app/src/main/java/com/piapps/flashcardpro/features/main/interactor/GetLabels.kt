package com.piapps.flashcardpro.features.main.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.features.main.entity.NavView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-09-27 at 23:34.
 */

class GetLabels
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(onResult: (ArrayList<NavView>) -> Unit = {}) {
        doAsync {
            val result = repository.getLabels()
            val list = arrayListOf<NavView>()
            result.forEach {
                list.add(it.toNavView())
            }
            uiThread {
                onResult(list)
            }
        }
    }
}