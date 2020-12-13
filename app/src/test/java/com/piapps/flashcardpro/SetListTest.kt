package com.piapps.flashcardpro

import com.piapps.flashcardpro.core.platform.MultiSortedList
import com.piapps.flashcardpro.features.main.entity.SetView
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

/**
 * Created by abduaziz on 12/13/20 at 10:24 PM.
 */

class SetListTest {

    @Test
    fun `test MultiSortedList = updated and sorted by id`() {
        val updateCallback = object : MultiSortedList.UpdateCallback<SetView> {
            override fun sortBy(i1: SetView, i2: SetView): Int {
                return i1.id.compareTo(i2.id)
            }

            override fun updateBy(oldItem: SetView, newItem: SetView): Int {
                return newItem.id.compareTo(oldItem.id)
            }
        }
        val list: MultiSortedList<SetView> = MultiSortedList(updateCallback)

        val n = 10_000

        // Add n flashcard sets with random ids
        for (i in 0 until n) {
            val randomId = Random.nextLong(0, System.currentTimeMillis())
            list.add(SetView(randomId, "", 0))
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
    fun `test MultiSortedList = updated by id + sorted by lastEdited`() {
        val updateCallback = object : MultiSortedList.UpdateCallback<SetView> {
            override fun sortBy(i1: SetView, i2: SetView): Int {
                return i1.lastEdited.compareTo(i2.lastEdited)
            }

            override fun updateBy(oldItem: SetView, newItem: SetView): Int {
                return newItem.id.compareTo(oldItem.id)
            }
        }
        val list: MultiSortedList<SetView> = MultiSortedList(updateCallback)

        val n = 10_000

        // Add n flashcard sets with random lastEdited times
        for (i in 0 until n) {
            val lastEdited = Random.nextLong(0, System.currentTimeMillis())
            list.add(SetView(i.toLong(), "", 0, lastEdited))
        }

        // list[i].lastEdited >= list[i+1].lastEdited
        var isSorted = true
        for (i in 1 until n) {
            val prev = list[i - 1]
            val current = list[i]
            if (prev.lastEdited > current.lastEdited)
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