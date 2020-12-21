package com.piapps.flashcardpro.features.main.interactor

import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.extension.doAsync
import com.piapps.flashcardpro.core.extension.log
import com.piapps.flashcardpro.core.extension.longLog
import java.lang.Integer.min
import javax.inject.Inject

/**
 * Created by abduaziz on 2020-12-15 at 21:18.
 */

class SaveSetOrders
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(sets: List<SetDb>, onResult: () -> Unit = {}) {
        //doAsync {
            val orders = arrayListOf<Long>()
            for (i in 0 until sets.size){
                orders.add(sets[i].order)
            }
            orders.sort()
            longLog(orders.toString())
            for (i in 0 until sets.size) {
                sets[i].order = orders[i]
                log("${sets[i].order} -> ${sets[i].title}")
            }
            repository.saveSets(sets)
        //}
    }
}