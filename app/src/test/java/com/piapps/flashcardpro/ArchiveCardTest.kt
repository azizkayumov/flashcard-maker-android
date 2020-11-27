package com.piapps.flashcardpro

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.DatabaseService
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.features.editor.SetPresenter
import com.piapps.flashcardpro.features.editor.interactor.ArchiveCard
import com.piapps.flashcardpro.features.editor.interactor.GetSetDetails
import com.piapps.flashcardpro.features.editor.interactor.SaveSet
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import kotlin.random.Random

/**
 * Created by abduaziz on 11/27/20 at 10:13 PM.
 */

class ArchiveCardTest : AbstractDatabaseTest() {

    @Test
    fun archiveCard_isCorrect() {
        // Create a set with random id
        val setId = System.currentTimeMillis()
        val set = SetDb(setId)

        // Create n cards for the set
        val n = 100
        val cards = arrayListOf<CardDb>()
        val id = System.currentTimeMillis()
        for (i in 0 until n) {
            cards.add(CardDb(id + i, setId))
        }
        set.count = n
        SaveSet(repository)(set, cards)

        // Archive some of the cards
        val archive = ArchiveCard(repository)
        var archivedCount = 0
        for (i in 0 until n) {
            if (Random.nextInt(0, 3) == 0){
                archive(cards[i])
                archivedCount++
            }
        }

        // Test
        // 1. The archive version of the set must be created with negative ID
        val archiveSet = repository.getSet(-setId)
        assertNotEquals(archiveSet, null)
        assertEquals(archiveSet?.id, -setId)

        // Test
        // 2. The count of the archive set must be equal to archivedCount
        assertEquals(archiveSet?.count, archivedCount)

        // Test
        // 3. The card count of the original set must be equal to n - archivedCount
        val originalSet = repository.getSet(setId)
        assertEquals(originalSet?.count, n - archivedCount)
    }
}