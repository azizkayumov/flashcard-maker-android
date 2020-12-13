package com.piapps.flashcardpro

import android.os.Build
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.features.editor.interactor.ArchiveCard
import com.piapps.flashcardpro.features.editor.interactor.SaveSet
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.random.Random

/**
 * Created by abduaziz on 11/27/20 at 10:13 PM.
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
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
            if (Random.nextInt(0, 3) == 0) {
                archive(cards[i])
                archivedCount++
                cards[i].setId = -setId
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
        var originalSet = repository.getSet(setId)
        assertEquals(originalSet?.count, n - archivedCount)

        // Test
        // 4. Archive the archived cards back to their original set
        for (i in 0 until n){
            if (cards[i].setId == -setId){
                archive(cards[i])
            }
        }
        originalSet = repository.getSet(setId)
        assertEquals(originalSet?.count, n)
    }


}