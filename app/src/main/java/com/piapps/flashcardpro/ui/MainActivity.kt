package com.piapps.flashcardpro.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Set
import com.piapps.flashcardpro.ui.controller.SetsController
import com.piapps.flashcardpro.util.Extensions
import com.piapps.flashcardpro.util.toHexColor
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var instance: MainActivity
    }

    val sets = arrayListOf<Set>()
    val all = arrayListOf<Set>()
    val favorites = arrayListOf<Set>()
    val lastEdited = arrayListOf<Set>()
    val trash = arrayListOf<Set>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        instance = this

        bottomNavigationViewMain.enableShiftingMode(false)
        bottomNavigationViewMain.enableItemShiftingMode(false)
        bottomNavigationViewMain.setTextVisibility(true)
        bottomNavigationViewMain.enableAnimation(false)
        bottomNavigationViewMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_all_sets -> {
                    loadAdapter(0)
                }
                R.id.bottom_nav_last_edited -> {
                    loadAdapter(1)
                }
                R.id.bottom_nav_favorite -> {
                    loadAdapter(2)
                }
                R.id.bottom_nav_trash -> {
                    loadAdapter(3)
                }
            }
            true
        }

        rvMain.layoutManager = LinearLayoutManager(this)
        update()
        val adapter = SetsController(lastEdited)
        rvMain.adapter = adapter
        bottomNavigationViewMain.currentItem = 1

        fab.setOnClickListener {
            if (bottomNavigationViewMain.currentItem != 3) {
                val set = Set(System.currentTimeMillis(), getString(R.string.new_set))
                set.lastEdited = System.currentTimeMillis()
                set.color = Extensions.color(set.id).toHexColor()
                Flashcards.instance.sets().put(set)
                val intent = Intent(this@MainActivity, SetActivity::class.java)
                intent.putExtra("id", set.id)
                intent.putExtra("isNew", true)
                startActivity(intent)
            } else {
                if (trash.isEmpty()) return@setOnClickListener
                alert(getString(R.string.are_you_sure_you_want_to_empty_the_trash)) {
                    yesButton {
                        trash.forEach {
                            Flashcards.instance.sets().remove(it.id)
                        }
                        trash.clear()
                        rvMain.adapter.notifyDataSetChanged()
                    }
                    noButton { it.dismiss() }
                }.show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            update()
            uiThread {
                loadAdapter(bottomNavigationViewMain.currentItem)
            }
        }

    }

    fun update() {
        sets.clear()
        all.clear()
        lastEdited.clear()
        favorites.clear()
        trash.clear()

        sets.addAll(Flashcards.instance.sets().all)
        all.addAll(sets.filter { !it.isTrash })
        all.sortByDescending { it.id }

        lastEdited.addAll(sets.filter { !it.isTrash })
        lastEdited.sortByDescending { it.lastEdited }

        favorites.addAll(sets.filter { !it.isTrash && it.isFavorite })
        favorites.sortByDescending { it.lastEdited }

        trash.addAll(sets.filter { it.isTrash })
        trash.sortByDescending { it.id }
    }

    fun loadAdapter(index: Int) {
        fab.setImageResource(if (index == 3) R.drawable.ic_clear_trash_white
        else R.drawable.ic_add_white)

        when (index) {
            0 -> {
                (rvMain.adapter as SetsController).sortingOrder = SetsController.SORTING_ORDER.CREATED_TIME
                (rvMain.adapter as SetsController).list = all
                (rvMain.adapter as SetsController).isTrash = false
                rvMain.adapter.notifyDataSetChanged()
            }
            1 -> {
                (rvMain.adapter as SetsController).sortingOrder = SetsController.SORTING_ORDER.LAST_EDITED
                (rvMain.adapter as SetsController).list = lastEdited
                (rvMain.adapter as SetsController).isTrash = false
                rvMain.adapter.notifyDataSetChanged()
            }
            2 -> {
                (rvMain.adapter as SetsController).sortingOrder = SetsController.SORTING_ORDER.LAST_EDITED
                (rvMain.adapter as SetsController).list = favorites
                (rvMain.adapter as SetsController).isTrash = false
                rvMain.adapter.notifyDataSetChanged()
            }
            3 -> {
                (rvMain.adapter as SetsController).sortingOrder = SetsController.SORTING_ORDER.CREATED_TIME
                (rvMain.adapter as SetsController).list = trash
                (rvMain.adapter as SetsController).isTrash = true
                rvMain.adapter.notifyDataSetChanged()
            }
        }
        var t = getString(R.string.no_sets_yet)
        if (index == 3) {
            t = getString(R.string.trash_is_empty)
        }
        if ((rvMain.adapter as SetsController).list.isEmpty()) {
            textViewNoSets.text = t
            textViewNoSets.visibility = View.VISIBLE
        } else {
            textViewNoSets.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
