package com.piapps.flashcardpro.core.db.tables

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient

/**
 * Created by abduaziz on 2019-09-27 at 22:15.
 */

@Entity
class CardDb(
    @Id(assignable = true)
    var id: Long,
    var setId: Long,
    var order: Int = 0,
    var front: String = "", // front text
    var back: String = "", // back text
    var frontColor: String = "", // hex value
    var backColor: String = "", // hex value
    var frontTextColor: String = "", // hex value
    var backTextColor: String = "", // hex value
    var frontImage: String = "", // path to front image
    var backImage: String = "", // path to back image

    var rating: Int = 0, // if true in Quiz, +1 else equals-1
    var flags: Int = 0,

    @Transient
    var isEditingBack: Boolean = false,
    @Transient
    var isSelected: Boolean = false
) {
    constructor() : this(0, 0)

    fun reverse() {
        val f = front
        val fImage = frontImage
        val fColor = frontColor
        val fTextColor = frontTextColor
        this.front = back
        this.frontImage = backImage
        this.frontColor = backColor
        this.frontTextColor = backTextColor
        this.back = f
        this.backImage = fImage
        this.backColor = fColor
        this.backTextColor = fTextColor
    }

    fun clone(): CardDb {
        return CardDb(
            id, setId, order,
            front, back,
            frontColor, backColor,
            frontTextColor, backTextColor,
            frontImage, backImage,
            rating, flags
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is CardDb) return false
        return other.id == id
    }
}