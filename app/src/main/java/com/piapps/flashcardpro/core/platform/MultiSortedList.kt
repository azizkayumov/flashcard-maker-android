package com.piapps.flashcardpro.core.platform

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by abduaziz on 5/26/18.
 *
 *   MultiSortedList is a wrapper component to ArrayList that keeps its elements in a sorted order
 *   using UpdateCallbackInterface. It is intended to be used inside recycler view adapters.
 *
 * */

open class MultiSortedList<T>(val updateCallback: UpdateCallback<T>, val adapter: RecyclerView.Adapter<*>? = null) {

    companion object {
        val TAG = "SORTEDLIST"
    }

    // internal initialList to hold elements by sortBy() -> visible to user
    val list: ArrayList<T> = arrayListOf()

    // internal initialList to hold elements by updateBy() -> not visible
    // UList = UpdateList
    private val uList: ArrayList<T> = arrayListOf()

    /*
    * Add item
    * 1. Search for existing element that satisfies updateBy()
    * 2. Remove the existing element if found
    * 3. Add the new item with sortBy()
    * 4. Notify if adapter is not null
    * */
    fun add(newItem: T, remove: Boolean = true) {
        if (remove)
            remove(newItem)

        // save to internal initialList by updateBy()
        var toBeStoredPosition = uList.binarySearch { updateCallback.updateBy(it, newItem) }
        if (toBeStoredPosition < 0) toBeStoredPosition = -(toBeStoredPosition + 1)
        uList.add(toBeStoredPosition, newItem)

        // save to UI initialList and notify changes
        var sortPosition = list.binarySearch { updateCallback.sortBy(it, newItem) }
        if (sortPosition < 0) sortPosition = -(sortPosition + 1)
        list.add(sortPosition, newItem)
        adapter?.notifyItemInserted(sortPosition)
    }

    /*
    * Update item
    * 1. Search for existing element that satisfies updateBy()
    * 2. If found, notify the element with payloads
    * 3. If not found, addNew
    * */
    fun update(updatedItem: T) {
        val storedPosition = uList.binarySearch { updateCallback.updateBy(it, updatedItem) }
        if (storedPosition >= 0 && storedPosition < uList.size) {
            uList[storedPosition] = updatedItem

            val sortPosition = list.binarySearch { updateCallback.sortBy(it, updatedItem) }
            if (sortPosition >= 0 && sortPosition < list.size) {
                list[sortPosition] = updatedItem
                adapter?.notifyItemChanged(sortPosition, 0)
            } else {
                add(updatedItem)
            }
        } else {
            add(updatedItem)
        }
    }

    /*
    * Remove and notify the adapter
    * */
    fun remove(removeItem: T): T? {
        val storedElementPosition = uList.binarySearch { updateCallback.updateBy(it, removeItem) }
        if (storedElementPosition >= 0 && storedElementPosition < uList.size) {

            // remove from internal initialList
            val itemTobeRemoved = uList[storedElementPosition]
            uList.removeAt(storedElementPosition)

            // remove from ui
            val removePosition = list.binarySearch { updateCallback.sortBy(it, itemTobeRemoved) }
            if (removePosition >= 0 && removePosition < list.size) {
                val t = list[removePosition]
                list.removeAt(removePosition)
                adapter?.notifyItemRemoved(removePosition)
                return t
            } else {
                Log.d(TAG, "couldn't binary search UI element($removePosition, ${list.size}) = $itemTobeRemoved")
                // so maybe a linear search
                for (i in 0 until list.size) {
                    if (updateCallback.sortBy(list[i], itemTobeRemoved) == 0) {
                        Log.d(TAG, "did linear search. removing element($i, ${list.size}) = ${list[i]}")
                        val t = list[i]
                        list.removeAt(i)
                        adapter?.notifyItemRemoved(i)
                        return t
                    }
                }
            }
        }
        return null
    }

    // can be accessed -> initialList.get(position) or initialList[position]
    operator fun get(pos: Int): T {
        return list[pos]
    }

    fun getNullable(pos: Int): T? {
        if (pos >= 0 && pos < list.size)
            return list[pos]
        return null
    }

    fun get_updateBy(pos: Int): T {
        return uList[pos]
    }

    fun indexOf(item: T): Int {
        return list.binarySearch { updateCallback.sortBy(it, item) }
    }

    fun indexOf_updateBy(item: T): Int {
        return list.binarySearch { updateCallback.updateBy(it, item) }
    }

    // for adapter use
    fun size(): Int {
        return list.size
    }

    fun isEmpty() = size() <= 0

    fun isNotEmpty() = size() > 0

    inline fun forEachIndexed(action: (Int, T) -> Unit) {
        for (index in 0 until size()) {
            action(index, get(index))
        }
    }

    // clear
    fun clear() {
        list.clear()
        uList.clear()
        adapter?.notifyDataSetChanged()
    }

    fun asList(): List<T> {
        val l = arrayListOf<T>()
        l.addAll(list)
        return l
    }

    /*
    * UpdateCallback is the main interface that is used to compare the elements.
    *   - sortBy() is used to locate new elements passed to MultiSortedList
    *   - updateBy() is used to update/remove elements
    *
    * Typical example would be Message model class which we want to:
    *   - Sort messages according to their dates
    *   - Update/Remove messages according to their randomIDs or IDs.
    * */
    interface UpdateCallback<T> {
        fun sortBy(i1: T, i2: T): Int
        fun updateBy(oldItem: T, newItem: T): Int
    }
}