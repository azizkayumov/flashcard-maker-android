package com.piapps.flashcards.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by abduaziz on 4/19/18.
 */

@Entity
class Label(@Id(assignable = true) var id: Long, var title: String)