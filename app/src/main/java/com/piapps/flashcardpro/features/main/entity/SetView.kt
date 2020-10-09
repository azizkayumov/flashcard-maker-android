package com.piapps.flashcardpro.features.main.entity

/**
 * Created by abduaziz on 2019-09-25 at 23:27.
 */

class SetView(
    var id: Long,
    var title: String,
    var flashcardsCount: Int,
    var color: String = "",
    var colorText: String = "",
    var isTrash: Boolean = false
)