package com.piapps.flashcardpro.features.editor

import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.platform.BaseView

/**
 * Created by abduaziz on 2019-09-30 at 21:37.
 */

interface SetEditorView : BaseView {

    companion object {
        val STATS = 0f
        val REVERSE = 1f
        val IMPORT = 2f
        val EXPORT = 3f
        val PASTE = 4f
        val SHUFFLE = 5f
        val SORT_ALPH = 6f

        val CARD_TEXT = 100f
        val CARD_IMAGE = 101f
        val CARD_DRAWING = 102f
        val CARD_TEXT_COLOR = 103f
        val CARD_BACK_COLOR = 104f
    }

    fun setTitle(s: String)
    fun setSetColor(c: Int)
    fun setSetColor(cs: String)
    fun showSetTitleEditor(current: String)
    fun showCards(cards: List<CardDb>)
    fun showNewCard(card: CardDb)
    fun cards(): List<CardDb>

    fun showLabels(labels: String)

    fun showSetExported(path: String)
    fun showCurrentCardPosition()

    fun showSelectionOptions()
    fun hideSelectionOptions()
    fun setSelectedCardsCounter(count: Int)
}