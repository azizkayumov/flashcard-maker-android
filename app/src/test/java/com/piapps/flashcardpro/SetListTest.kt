package com.piapps.flashcardpro

import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.platform.MultiSortedList
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

/**
 * Created by abduaziz on 12/13/20 at 10:24 PM.
 */

class SetListTest {

    @Test
    fun `test MultiSortedList = updated and sorted by id`() {
        val updateCallback = object : MultiSortedList.UpdateCallback<SetDb> {
            override fun sortBy(i1: SetDb, i2: SetDb): Int {
                return i1.id.compareTo(i2.id)
            }

            override fun updateBy(oldItem: SetDb, newItem: SetDb): Int {
                return newItem.id.compareTo(oldItem.id)
            }
        }
        val list: MultiSortedList<SetDb> = MultiSortedList(updateCallback)

        val n = 10_000

        // Add n flashcard sets with random ids
        for (i in 0 until n) {
            val randomId = Random.nextLong(0, System.currentTimeMillis())
            list.add(SetDb(randomId))
        }

        // list[i].id >= list[i+1].id
        var isSorted = true
        for (i in 1 until n) {
            val prev = list[i - 1]
            val current = list[i]
            if (prev.id > current.id)
                isSorted = false
        }
        assert(isSorted)
    }

    @Test
    fun `test MultiSortedList = updated by id + sorted by order`() {
        val updateCallback = object : MultiSortedList.UpdateCallback<SetDb> {
            override fun sortBy(i1: SetDb, i2: SetDb): Int {
                return i1.order.compareTo(i2.order)
            }

            override fun updateBy(oldItem: SetDb, newItem: SetDb): Int {
                return newItem.id.compareTo(oldItem.id)
            }
        }
        val list: MultiSortedList<SetDb> = MultiSortedList(updateCallback)

        val n = 10_000

        // Add n flashcard sets with random lastEdited times
        for (i in 0 until n) {
            list.add(SetDb(id = n - i.toLong(), order = i.toLong()))
        }

        // list[i].lastEdited >= list[i+1].lastEdited
        var isSorted = true
        for (i in 1 until n) {
            val prev = list[i - 1]
            val current = list[i]
            if (prev.order > current.order)
                isSorted = false
        }
        assert(isSorted)

        // list must contain distinct elements
        val set = hashSetOf<Long>()
        for (i in 0 until n) {
            set.add(list[i].id)
        }
        assertEquals(set.size, n)
    }
}