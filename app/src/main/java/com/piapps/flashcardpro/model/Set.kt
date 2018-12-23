package com.piapps.flashcardpro.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by abduaziz on 4/18/18.
 */

@Entity
class Set(@Id(assignable = true) var id: Long,
          var title: String = "",
          var count: Int = 0,
          var useCount: Int = 0,
          var labels: String = "",
          var lastEdited: Long = 0L,
          var color: String = "",
          var isTrash: Boolean = false,
          var isFavorite: Boolean = false){
    constructor() : this(0)
}