package com.piapps.flashcardpro.features.editor

import com.piapps.flashcardpro.core.db.Clipboard
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.core.settings.Settings
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
    lateinit var archiveCard: ArchiveCard

    @Inject
    lateinit var deleteCards: DeleteCards

    @Inject
    lateinit var exportToCSV: ExportToCSV

    @Inject
    lateinit var importFromCSV: ImportFromCSV

    @Inject
    lateinit var clipboard: Clipboard

    @Inject
    lateinit var settings: Settings

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
        set.color = settings.getDefaultCardBackgroundColor()
        set.textColor = settings.getDefaultCardTextColor()
        view?.setTitle(set.title)
        view?.setColors(settings.getDefaultCardBackgroundColor(), settings.getDefaultCardTextColor())
        saveSet(set)
    }

    fun showSetDetails(set: SetDb) {
        this.set = set
        view?.setTitle(set.title)
        view?.showLabels(set.labels)
        view?.setColors(set.color, set.textColor ?: "")
    }

    fun loadCards() {
        getCards(set.id) {
            view?.showCards(it)
        }
    }

    fun editSetName(title: String) {
        set.title = title
        saveSet(set)
    }

    fun setDefaultColor(color: String) {
        set.color = color
        settings.setDefaultCardBackgroundColor(color)
        saveSet(set)
    }

    fun setDefaultTextColor(color: String) {
        set.textColor = color
        settings.setDefaultCardTextColor(color)
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
        card.frontTextSize = settings.getDefaultCardTextSize()
        card.backTextSize = settings.getDefaultCardTextSize()
        set.count++
        saveSet(set, listOf(card))
        view?.showNewCard(card)
    }

    fun archiveCardOffline(card: CardDb) {
        set.count--
        archiveCard(card)
    }

    fun moveSetToTrash() {
        set.isTrash = true
        saveSet(set)
    }

    fun editCardText(text: String) {
        editingCard.front = text
        saveCard(editingCard)
    }

    fun editCardBackgroundImage(path: String) {
        editingCard.frontImage = path
        saveCard(editingCard)
    }

    fun defaultFontSize() = editingCard.frontTextSize

    fun editCardFontSize(size: Float) {
        editingCard.frontTextSize = size
        settings.setDefaultCardTextSize(size)
        saveCard(editingCard)
    }

    fun editAllCardsFontSize(size: Float) {
        settings.setDefaultCardTextSize(size)
        view?.cards()?.forEach {
            it.frontTextSize = size
            it.backTextSize = size
        }
        saveSet(set, view?.cards() ?: listOf())
    }

    fun clearCardSide() {
        editingCard.front = ""
        editingCard.frontImage = ""
        saveCard(editingCard)
        view?.updateCard(editingCard)
    }

    fun saveCard(card: CardDb) {
        saveCard(card)
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
        editSetName(file.nameWithoutExtension)
        view?.setTitle(file.nameWithoutExtension)
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

        view?.showCurrentCardPosition()
        autoSave()
    }
    
    fun deleteSelectedCards(){
        deleteCards(selectedCards)
        view?.hideSelectionOptions()
    }
}