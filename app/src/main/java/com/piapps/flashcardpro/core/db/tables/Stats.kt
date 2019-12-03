package com.piapps.flashcardpro.core.db.tables

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Uid

/**
 * Created by abduaziz on 2019-10-31 at 22:30.
 */

@Entity
class Stats(
    @Id(assignable = true)
    var id: Long,
    var setId: Long = 1,
    var accuracy: Int = 0,
    var numberOfCards: Int = 0,
    var studyDuration: Long = 0
) {
    constructor() : this(0)

    override fun toString(): String {
        return "Stats(id=$id, setId=$setId, accuracy=$accuracy, numberOfCards=$numberOfCards, studyDuration=$studyDuration)"
    }


}