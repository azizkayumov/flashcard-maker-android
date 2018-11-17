package com.piapps.flashcardpro.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.ui.controller.LabelsController
import kotlinx.android.synthetic.main.activity_labels.*

class LabelsActivity : AppCompatActivity() {

    val labels = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labels)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val l = intent.getStringExtra("labels")
        l.split(LabelsController.DELIMITER).forEach {
            labels.add(it)
        }


    }
}
