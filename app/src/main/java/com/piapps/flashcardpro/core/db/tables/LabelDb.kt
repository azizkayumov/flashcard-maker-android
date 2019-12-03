package com.piapps.flashcardpro.core.db.tables

import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.features.main.entity.NavView
import io.objectbox.annotation.*

/**
 * Created by abduaziz on 2019-09-27 at 22:29.
 */

@Entity
class LabelDb(
    @Id(assignable = true)
    var id: Long,

    @Unique // throws UniqueViolationException
    var title: String = "",

    @Transient
    var selected: Boolean = false
) {
    fun toNavView() = NavView(id, NavView.TYPE_LABEL, title, R.drawable.ic_label)
}