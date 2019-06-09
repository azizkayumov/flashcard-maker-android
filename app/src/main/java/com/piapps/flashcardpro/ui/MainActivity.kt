package com.piapps.flashcardpro.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.abduaziz.lib.FilePicker
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Label
import com.piapps.flashcardpro.model.Set
import com.piapps.flashcardpro.ui.controller.FilterLabelsController
import com.piapps.flashcardpro.ui.controller.LabelsController
import com.piapps.flashcardpro.ui.controller.SetsController
import com.piapps.flashcardpro.util.CSVUtils
import com.piapps.flashcardpro.util.Extensions
import com.piapps.flashcardpro.util.toHexColor
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.io.File


class MainActivity : AppCompatActivity(), FilterLabelsController.OnLabelClickedListener {

    companion object {
        lateinit var instance: MainActivity
    }

    val sets = arrayListOf<Set>()
    val all = arrayListOf<Set>()
    val favorites = arrayListOf<Set>()
    val lastEdited = arrayListOf<Set>()
    val trash = arrayListOf<Set>()

    var activeLabel = Label(-1L, "")
    val labels = arrayListOf<Label>()
    lateinit var labelsController: FilterLabelsController

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

        rvLabels.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        labelsController = FilterLabelsController(labels)
        rvLabels.adapter = labelsController
        labelsController.onLabelClickedListener = this@MainActivity

        update()
        rvMain.layoutManager = LinearLayoutManager(this)
        val adapter = SetsController(arrayListOf())
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
                        loadAdapter(1)
                    }
                    noButton { it.dismiss() }
                }.show()
            }
        }
    }

    override fun onLabelClicked(l: Label, pos: Int) {
        labelsController.labels.forEachIndexed { index, label ->
            if (index != pos) label.isSelected = false
        }
        l.isSelected = !l.isSelected
        labelsController.notifyDataSetChanged()
        if (l.isSelected) {
            activeLabel.apply {
                id = l.id
                title = l.title
                isSelected = l.isSelected
            }
        } else {
            activeLabel.apply {
                id = -1L
                title = LabelsController.DELIMITER
                isSelected = l.isSelected
            }
        }
        loadAdapter(bottomNavigationViewMain.currentItem)
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            update()
            uiThread {
                loadAdapter(bottomNavigationViewMain.currentItem)
                labelsController.labels = labels
                labelsController.notifyDataSetChanged()
                rvLabels.visibility = View.GONE
            }
        }
    }

    fun update() {
        activeLabel = Label(-1L, "")

        sets.clear()
        all.clear()
        lastEdited.clear()
        favorites.clear()
        trash.clear()
        labels.clear()

        sets.addAll(Flashcards.instance.sets().all)
        all.addAll(sets.filter { !it.isTrash })
        all.sortByDescending { it.id }

        lastEdited.addAll(sets.filter { !it.isTrash })
        lastEdited.sortByDescending { it.lastEdited }

        favorites.addAll(sets.filter { !it.isTrash && it.isFavorite })
        favorites.sortByDescending { it.lastEdited }

        trash.addAll(sets.filter { it.isTrash })
        trash.sortByDescending { it.id }

        labels.addAll(Flashcards.instance.labels().all.sortedBy { it.title })
    }

    fun loadAdapter(index: Int) {
        fab.setImageResource(if (index == 3) R.drawable.ic_clear_trash_white
        else R.drawable.ic_add_white)

        when (index) {
            0 -> {
                (rvMain.adapter as SetsController).sortingOrder = SetsController.SORTING_ORDER.CREATED_TIME
                (rvMain.adapter as SetsController).list.clear()
                if (activeLabel.id != -1L)
                    (rvMain.adapter as SetsController).list.addAll(all.filter { it.labels.contains(activeLabel.title) })
                else
                    (rvMain.adapter as SetsController).list.addAll(all)
                (rvMain.adapter as SetsController).isTrash = false
                rvMain.adapter?.notifyDataSetChanged()
            }
            1 -> {
                (rvMain.adapter as SetsController).sortingOrder = SetsController.SORTING_ORDER.LAST_EDITED
                (rvMain.adapter as SetsController).list.clear()
                if (activeLabel.id != -1L)
                    (rvMain.adapter as SetsController).list.addAll(lastEdited.filter { it.labels.contains(activeLabel.title) })
                else
                    (rvMain.adapter as SetsController).list.addAll(lastEdited)
                (rvMain.adapter as SetsController).isTrash = false
                rvMain.adapter?.notifyDataSetChanged()
            }
            2 -> {
                (rvMain.adapter as SetsController).sortingOrder = SetsController.SORTING_ORDER.LAST_EDITED
                (rvMain.adapter as SetsController).list.clear()
                if (activeLabel.id != -1L)
                    (rvMain.adapter as SetsController).list.addAll(favorites.filter { it.labels.contains(activeLabel.title) })
                else
                    (rvMain.adapter as SetsController).list.addAll(favorites)
                (rvMain.adapter as SetsController).isTrash = false
                rvMain.adapter?.notifyDataSetChanged()
            }
            3 -> {
                (rvMain.adapter as SetsController).sortingOrder = SetsController.SORTING_ORDER.CREATED_TIME
                (rvMain.adapter as SetsController).list.clear()
                if (activeLabel.id != -1L)
                    (rvMain.adapter as SetsController).list.addAll(trash.filter { it.labels.contains(activeLabel.title) })
                else
                    (rvMain.adapter as SetsController).list.addAll(trash)
                (rvMain.adapter as SetsController).isTrash = true
                rvMain.adapter?.notifyDataSetChanged()
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

    fun checkAndOpenFileExplorer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), CSVUtils.READ_EXTERNAL_STORAGE)
            return
        }
        openFileExplorer()
    }

    fun openFileExplorer() {
        val filePicker = FilePicker()
        filePicker.addOnFilesSelected(object : FilePicker.OnFilesSelected {
            override fun onFilesSelected(selectedFiles: List<File>) {
                importCSVFile(selectedFiles)
            }
        })
        supportFragmentManager.beginTransaction()
                .add(filePicker, "FilePicker")
                .addToBackStack("FilePicker")
                .commitAllowingStateLoss()
        //filePicker.show(supportFragmentManager, "FilePicker")
    }

    fun importCSVFile(selectedFiles: List<File>) {
        if (selectedFiles.isEmpty()) return
        val loadingDialog = progressDialog("") {
            setMessage(getString(R.string.importing_from_csv_file))
            progress = 0
            max = selectedFiles.size
            setCancelable(false)
        }
        loadingDialog.show()
        selectedFiles.forEachIndexed { index, file ->
            loadingDialog.setMessage(getString(R.string.importing_from_csv_file) + " ${file.name}")
            loadingDialog.progress = index + 1
            Handler().postDelayed({
                doAsync {
                    val set = CSVUtils.importFromCSV(file)
                    uiThread {
                        set?.let {
                            loadingDialog.setMessage(getString(R.string.imported_new_set))
                        }
                        if (index == selectedFiles.size - 1) {

                            loadingDialog.hide()
                            doAsync {
                                update()
                                uiThread {
                                    loadAdapter(bottomNavigationViewMain.currentItem)
                                }
                            }
                        }
                    }
                }
            }, 1000)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CSVUtils.READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFileExplorer()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
                if (rvLabels.visibility == View.VISIBLE) {
                    activeLabel = Label(-1L, "")
                    labelsController.labels.forEach { it.isSelected = false }
                    labelsController.notifyDataSetChanged()
                    rvLabels.visibility = View.GONE
                    loadAdapter(bottomNavigationViewMain.currentItem)
                } else {
                    rvLabels.visibility = View.VISIBLE
                }
                return true
            }
            R.id.action_import -> {
                checkAndOpenFileExplorer()
                return true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
