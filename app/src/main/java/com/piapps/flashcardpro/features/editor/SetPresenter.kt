package com.piapps.flashcardpro.features.editor

import com.piapps.flashcardpro.core.db.Clipboard
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.extension.color
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.features.editor.interactor.*
import java.io.File
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-09-30 at 21:36.
 */

class SetPresenter(var view: SetEditorView?) : BasePresenter(view) {

    @Inject
    lateinit var getSet: GetSetDetails

    @Inject
    lateinit var saveSet: SaveSet

    @Inject
    lateinit var getCards: GetCards

    @Inject
    lateinit var deleteCard: DeleteCard

    @Inject
    lateinit var exportToCSV: ExportToCSV

    @Inject
    lateinit var importFromCSV: ImportFromCSV

    @Inject
    lateinit var clipboard: Clipboard

    private var setId = 0L
    var set = SetDb()
    var editingCard = CardDb()

    var selectedCards = arrayListOf<CardDb>()

    fun loadSetDetails(id: Long) {
        this.setId = id
        getSet(setId) {
            it.callEither(::createNewSet, ::showSetDetails)
        }
    }

    fun createNewSet(failure: Failure) {
        set = SetDb(id = System.currentTimeMillis())
        setId = set.id
        set.lastEdited = System.currentTimeMillis()
        view?.setTitle(set.title)
        saveSet(set)
    }

    fun showSetDetails(set: SetDb) {
        this.set = set
        view?.setTitle(set.title)
        if (set.color.isNotBlank())
            view?.setSetColor(set.color)
        else
            view?.setSetColor(set.id.color())

        view?.showLabels(set.labels)
    }

    fun loadCards() {
        getCards(set.id) {
            view?.showCards(it)
        }
    }

    fun editSetName(title: String) {
        set.title = title
        set.lastEdited = System.currentTimeMillis()
        saveSet(set)
    }

    fun setDefaultColor(color: String) {
        set.color = color
        saveSet(set)
    }

    fun saveLabels(labels: String) {
        set.labels = labels
        saveSet(set)
    }

    private var lastCardAddedTime = 0L
    fun addNewCard() {
        if (lastCardAddedTime > System.currentTimeMillis() - 300) return
        lastCardAddedTime = System.currentTimeMillis()
        val card = CardDb(System.currentTimeMillis(), set.id, order = set.count)
        set.count++
        set.lastEdited = System.currentTimeMillis()
        saveSet(set, listOf(card))
        view?.showNewCard(card)
    }

    fun deleteCardOffline(card: CardDb) {
        set.count--
        set.lastEdited = System.currentTimeMillis()
        deleteCard(card)
    }

    fun moveSetToTrash() {
        set.isTrash = true
        saveSet(set)
    }

    fun editCardText(text: String) {
        if (editingCard.isEditingBack)
            editingCard.back = text
        else
            editingCard.front = text
        saveCard(editingCard)
    }

    fun editCardBackgroundColor(color: String) {
        if (editingCard.isEditingBack)
            editingCard.backColor = color
        else
            editingCard.frontColor = color
        saveCard(editingCard)
    }

    fun editCardTextColor(color: String) {
        if (editingCard.isEditingBack)
            editingCard.backTextColor = color
        else
            editingCard.frontTextColor = color
        saveCard(editingCard)
    }

    fun editCardBackgroundImage(path: String) {
        if (editingCard.isEditingBack)
            editingCard.backImage = path
        else
            editingCard.frontImage = path
        saveCard(editingCard)
    }

    fun saveCard(card: CardDb) {
        set.lastEdited = System.currentTimeMillis()
        saveSet(set, listOf(card))
    }

    fun exportCSV() {
        exportToCSV(set.id) {
            it.callEither(::handleFailure, ::handleExportSuccess)
        }
    }

    private fun handleExportSuccess(path: String) {
        view?.showSetExported(path)
    }

    fun importCSV(file: File) {
        importFromCSV(set, file) {
            it.callEither(::handleFailure, ::handleImportSuccess)
        }
    }

    fun handleImportSuccess(cards: List<CardDb>) {
        view?.showCards(cards)
    }

    fun autoSave() {
        val cards = view?.cards() ?: arrayListOf()
        set.count = cards.size
        saveSet(set, cards)
    }

    fun toggleCardSelection(card: CardDb) {
        if (card.isSelected)
            selectedCards.add(card.clone())
        else
            selectedCards.removeAll { it.id == card.id }

        view?.setSelectedCardsCounter(selectedCards.size)
        if (selectedCards.isNotEmpty())
            view?.showSelectionOptions()
        else
            view?.hideSelectionOptions()
    }

    fun cancelCardsSelection() {
        selectedCards.clear()
        view?.hideSelectionOptions()
    }

    fun copyCards() {
        val now = System.currentTimeMillis()
        // we're copying cards, give new ids to the selected cards
        for (i in 0 until selectedCards.size)
            selectedCards[i].id = now + i
        clipboard.addToClipboard(selectedCards)
        view?.hideSelectionOptions()
    }

    fun moveCards() {
        clipboard.addToClipboard(selectedCards)
        view?.hideSelectionOptions()
    }

    fun canPasteCards(): Boolean {
        return !clipboard.isEmpty()
    }

    fun pasteCards() {
        val cards = clipboard.getBufferedCards()
        clipboard.clearClipboard()

        for (i in 0 until cards.size)
            cards[i].setId = setId
        view?.showCards(cards)

        autoSave()
    }
}