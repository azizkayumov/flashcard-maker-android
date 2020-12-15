package com.piapps.flashcardpro.core.db

import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.db.tables.LabelDb
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.db.tables.Stats
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.functional.Either
import com.piapps.flashcardpro.core.settings.Settings
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-09-27 at 22:37.
 */

interface DatabaseRepository {

    fun save(set: SetDb)
    fun saveSets(sets: List<SetDb>)
    fun getSet(id: Long): SetDb?
    fun delete(set: SetDb)

    fun getAllSets(): List<SetDb>
    fun getArchiveSets(): List<SetDb>
    fun getTrashSets(): List<SetDb>
    fun getLabelSets(l: String): List<SetDb>

    fun save(card: CardDb)
    fun save(cards: List<CardDb>)
    fun getCard(id: Long): CardDb?
    fun delete(card: CardDb)
    fun deleteCards(cards: List<CardDb>)
    fun getCards(setId: Long): List<CardDb>
    fun getCardsByRating(setId: Long): List<CardDb>

    fun save(label: LabelDb): Either<Failure, LabelDb> // throws UniqueViolationException for labels
    fun delete(label: LabelDb)
    fun getLabels(): List<LabelDb>
    fun deleteSets(list: List<SetDb>)

    fun save(stats: Stats)
    fun getStats(setId: Long, from: Long, to: Long): List<Stats>
    fun clearStats(setId: Long)

    class Database
    @Inject constructor(private val service: DatabaseService, private val settings: Settings) :
        DatabaseRepository {

        override fun save(set: SetDb) {
            service.saveSet(set)
        }

        override fun saveSets(sets: List<SetDb>) {
            service.saveSets(sets)
        }

        override fun getSet(id: Long): SetDb? {
            return service.getSet(id)
        }

        override fun delete(set: SetDb) {
            service.deleteSet(set)
        }

        override fun getAllSets(): List<SetDb> {
            return service.getAllSets()
        }

        override fun getArchiveSets(): List<SetDb> {
            return service.getArchiveSets()
        }

        override fun getTrashSets(): List<SetDb> {
            return service.getTrashSets()
        }

        override fun getLabelSets(l: String): List<SetDb> {
            return service.getLabelSets(l)
        }

        override fun save(card: CardDb) {
            service.saveCard(card)
        }

        override fun save(cards: List<CardDb>) {
            service.saveCards(cards)
        }

        override fun getCard(id: Long): CardDb? {
            return service.getCard(id)
        }

        override fun delete(card: CardDb) {
            service.deleteCard(card)
        }

        override fun deleteCards(cards: List<CardDb>) {
            service.deleteCards(cards)
        }

        override fun getCards(setId: Long): List<CardDb> {
            return service.getCards(setId)
        }

        override fun getCardsByRating(setId: Long): List<CardDb> {
            return service.getCardsByRating(setId)
        }

        override fun save(label: LabelDb): Either<Failure, LabelDb> {
            try {
                service.saveLabel(label)
                return Either.Right(label)
            } catch (e: Exception) { // UniqueViolationException ;-)
                return Either.Left(Failure.LabelMustBeUnique)
            }
        }

        override fun delete(label: LabelDb) {
            service.deleteLabel(label)
        }

        override fun getLabels(): List<LabelDb> {
            if (settings.isUserFirstTime()) {
                val id = System.currentTimeMillis()
                service.saveLabel(LabelDb(id, "Important"))
                service.saveLabel(LabelDb(id + 1, "Todo"))
                service.saveLabel(LabelDb(id + 2, "Dictionary"))
                settings.setUserNotFirstTime()
            }
            return service.getLabels()
        }

        override fun deleteSets(list: List<SetDb>) {
            service.deleteSets(list)
        }

        override fun save(stats: Stats) {
            service.saveStats(stats)
        }

        override fun getStats(setId: Long, from: Long, to: Long): List<Stats> {
            return service.getStats(setId, from, to)
        }

        override fun clearStats(setId: Long) {
            service.clearStats(setId)
        }
    }
}