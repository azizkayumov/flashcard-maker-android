package com.piapps.flashcardpro.core.db

import com.piapps.flashcardpro.core.db.tables.CardDb
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by abduaziz on 2020-05-24 at 03:30.
 */

/*
* Temporary storage to hold cards
* and is used to copy, move cards between sets
* */

@Singleton
class Clipboard
@Inject constructor(){

    private val buffer = mutableSetOf<CardDb>()

    fun addToClipboard(cards: List<CardDb>) {
        buffer.addAll(cards)
    }

    fun getBufferedCards(): List<CardDb> {
        return buffer.toList()
    }

    fun clearClipboard() {
        buffer.clear()
    }

    fun isEmpty() = buffer.isEmpty()

}