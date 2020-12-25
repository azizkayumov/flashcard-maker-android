package com.piapps.flashcardpro.core.support

import com.piapps.flashcardpro.BuildConfig
import com.piapps.flashcardpro.core.db.DatabaseRepository
import javax.inject.Inject

/**
 * Created by abduaziz on 12/21/20 at 9:51 PM.
 *
 * This class aims to initialize sets' order with their respective ids to support older versions than 28 (see build.gradle).
 * It should be used once, otherwise it may break the user-defined orders on every usage
 */

class InitOrders
@Inject constructor(private val repository: DatabaseRepository) {
    fun justDoIt(){
        val sets = repository.getAllSets()
        for (i in sets.indices){
            sets[i].order = sets[i].id
        }
        repository.saveSets(sets)
    }
}