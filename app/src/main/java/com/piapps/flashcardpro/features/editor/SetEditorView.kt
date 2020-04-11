package com.piapps.flashcardpro.features.editor

import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.platform.BaseView

/**
 * Created by abduaziz on 2019-09-30 at 21:37.
 */

interface SetEditorView : BaseView {

    companion object {
        val STATS = 0L
        val REVERSE = 1L
        val IMPORT = 2L
        val EXPORT = 3L

        val CARD_TEXT = 100L
        val CARD_IMAGE = 101L
        val CARD_DRAWING = 102L
        val CARD_TEXT_COLOR = 103L
        val CARD_BACK_COLOR = 104L
    }

    fun setTitle(s: String)
    fun setSetColor(c: Int)
    fun setSetColor(cs: String)
    fun showSetTitleEditor(current: String)
    fun showCards(cards: List<CardDb>)
    fun showNewCard(card: CardDb)

    fun showLabels(labels: String)

    fun showSetExported(path: String)
    fun showCurrentCardPosition()
}