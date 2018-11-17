package com.piapps.flashcardpro.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Label
import com.piapps.flashcardpro.model.Label_
import com.piapps.flashcardpro.ui.controller.LabelsController
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.input.SimpleInputDialog
import kotlinx.android.synthetic.main.activity_labels.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class LabelsActivity : AppCompatActivity(), SimpleDialog.OnDialogResultListener, LabelsController.OnLabelClickedListener {

    val DIALOG_ADD_LABEL = "ADD_LABEL"
    lateinit var controller: LabelsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labels)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.labels)

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rvLabels.layoutManager = layoutManager
        rvLabels.itemAnimator = null
        val l = intent.getStringExtra("labels") ?: ""
        controller = LabelsController(l)
        controller.onLabelClickedListener = this
        rvLabels.adapter = controller

        if (l.isBlank())
            tvNoLabels.visibility = View.VISIBLE

        fab.setOnClickListener {
            tvNoLabels.visibility = View.GONE
            SimpleInputDialog()
                    .hint(R.string.enter_label)
                    .show(this, DIALOG_ADD_LABEL)
        }
    }

    override fun onLabelClicked(l: String, position: Int) {
        alert {
            messageResource = R.string.are_you_sure_you_want_to_delete_the_label
            yesButton {
                controller.labels.removeAt(position)
                controller.notifyItemRemoved(position)
            }
            noButton {
                it.dismiss()
            }
        }.show()
    }

    fun saveLabel(l: String) {
        val query = Flashcards.instance.labels().query().equal(Label_.title, l).build()
        if (query.find().isEmpty()) {
            Flashcards.instance.labels().put(Label(System.currentTimeMillis(), l))
        }
    }

    override fun onResult(dialogTag: String, which: Int, extras: Bundle): Boolean {
        if (which == 0) return true

        if (dialogTag == DIALOG_ADD_LABEL) {
            val label = extras.getString(SimpleInputDialog.TEXT)
            controller.addLabel(label)
            saveLabel(label)
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_labels, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        if (item?.itemId == R.id.action_ok) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("labels", controller.labels)
            })
            finish()
            return true
        }

        return true
    }
}
