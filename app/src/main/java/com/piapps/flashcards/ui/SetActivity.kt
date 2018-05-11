package com.piapps.flashcards.ui

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import com.piapps.flashcards.R
import com.piapps.flashcards.application.Flashcards
import com.piapps.flashcards.model.Card
import com.piapps.flashcards.model.Card_
import com.piapps.flashcards.model.Set
import com.piapps.flashcards.ui.controller.SetController
import com.piapps.flashcards.ui.fragment.CardFragment
import com.piapps.flashcards.util.Extensions
import com.piapps.flashcards.util.toColor
import com.piapps.flashcards.util.toHexColor
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.color.SimpleColorDialog
import eltos.simpledialogfragment.input.SimpleInputDialog
import kotlinx.android.synthetic.main.activity_set.*
import org.jetbrains.anko.toast

class SetActivity : AppCompatActivity(), SimpleDialog.OnDialogResultListener {

    lateinit var set: Set

    // dialogs
    val DIALOG_SET_COLOR = "DIALOG_SET_COLOR"
    val DIALOG_SET_NAME = "DIALOG_SET_NAME"

    // cards front/back colors
    val DIALOG_SET_CARD_COLOR_FRONT = "DIALOG_SET_CARD_COLOR_FRONT"
    val DIALOG_SET_CARD_COLOR_BACK = "DIALOG_SET_CARD_COLOR_BACK"
    // card front/back text colors
    val DIALOG_SET_TEXT_COLOR_FRONT = "DIALOG_SET_TEXT_COLOR_FRONT"
    val DIALOG_SET_TEXT_COLOR_BACK = "DIALOG_SET_TEXT_COLOR_BACK"


    lateinit var setController: SetController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        set = Flashcards.instance.sets().get(intent.getLongExtra("id", 0L))
        supportActionBar?.title = set.title

        val isNewlyCreatedSet = intent.getBooleanExtra("isNew", false)
        if (isNewlyCreatedSet) {
            SimpleInputDialog()
                    .text(set.title)
                    .title(R.string.set_name)
                    .show(this, DIALOG_SET_NAME)
        }

        if (!set.color.isBlank()) {
            toolbar.setBackgroundColor(set.color.toColor())
            changeStatusBarColor(set.color.toColor())
        } else {
            toolbar.setBackgroundColor(Extensions.color(set.id))
            changeStatusBarColor(Extensions.color(set.id))
        }

        toolbar.setOnClickListener {
            SimpleInputDialog()
                    .text(set.title)
                    .title(R.string.set_name)
                    .show(this, DIALOG_SET_NAME)
        }

        setController = SetController(set.id, supportFragmentManager)
        viewPager.setPageTransformer(true, SetController.ZoomOutPageTransformer())
        viewPager.adapter = setController
        indicator.setViewPager(viewPager)
        setController.registerDataSetObserver(indicator.dataSetObserver)

        val query = Flashcards.instance.cards()
                .query().equal(Card_.setId, set.id)
                .build()
        query.find().forEach {
            setController.addFragment(CardFragment.newInstance(it.id))
        }
        if (setController.list.isEmpty())
            linearLayoutCardEditor.visibility = View.GONE
        else
            linearLayoutCardEditor.visibility = View.VISIBLE

        fab.setOnClickListener {
            addNewCard()
        }

        imageViewCardColor.setOnClickListener {
            val card = setController.list[viewPager.currentItem]
            val tag = if (card.side() == 0) DIALOG_SET_CARD_COLOR_FRONT else DIALOG_SET_CARD_COLOR_BACK
            SimpleColorDialog()
                    .title(R.string.card_background_color)
                    .colors(Extensions.setColors())
                    .allowCustom(true)
                    .cancelable(true)
                    .show(this, tag)
        }

        imageViewTextColor.setOnClickListener {
            val card = setController.list[viewPager.currentItem]
            val tag = if (card.side() == 0) DIALOG_SET_TEXT_COLOR_FRONT else DIALOG_SET_TEXT_COLOR_BACK
            SimpleColorDialog()
                    .title(R.string.card_text_color)
                    .colors(Extensions.setDarkerColors())
                    .allowCustom(true)
                    .cancelable(true)
                    .show(this, tag)
        }

        imageViewInsertImage.setOnClickListener {
            toast("Insert image Card ${viewPager.currentItem}")
        }

        imageViewInsertAudio.setOnClickListener {
            toast("Insert audio Card ${viewPager.currentItem}")
        }
    }

    fun addNewCard() {

        linearLayoutCardEditor.visibility = VISIBLE

        set.lastEdited = System.currentTimeMillis()
        Flashcards.instance.sets().put(set)

        val card = Card(System.currentTimeMillis(), set.id)
        card.front = getString(R.string.example_front)
        card.back = getString(R.string.example_back)
        card.frontColor = set.color
        card.backColor = set.color
        Flashcards.instance.cards().put(card)

        setController.addFragment(CardFragment.newInstance(card.id))
        viewPager.currentItem = setController.list.size - 1
    }

    fun deleteCard() {
        // remember the current item to be removed
        val currentItem = viewPager.currentItem

        // animate to give a feeling of removing a card
        var nextCurrentItem = viewPager.currentItem + 1
        if (nextCurrentItem >= setController.list.size)
            nextCurrentItem = viewPager.currentItem - 1
        if (nextCurrentItem >= 0)
            viewPager.currentItem = nextCurrentItem

        // give time to show the slide next animation
        // executes after 200 millis
        Handler().postDelayed(Runnable {
            // update 'Last edited' time
            set.lastEdited = System.currentTimeMillis()
            Flashcards.instance.sets().put(set)

            // delete from db
            val card = setController.list[currentItem].card
            Flashcards.instance.cards().remove(card)

            // delete from ui
            setController.deleteFragment(currentItem)

            // hide editor view
            if (setController.list.isEmpty()) {
                linearLayoutCardEditor.visibility = GONE
            }
        }, 200)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_set, menu)
        menu.getItem(0).icon = if (set.isFavorite) ContextCompat.getDrawable(this, R.drawable.ic_star_black) else
            ContextCompat.getDrawable(this, R.drawable.ic_star_empty_black)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        if (item?.itemId == R.id.action_favorite) {
            set.isFavorite = !set.isFavorite
            set.lastEdited = System.currentTimeMillis()
            Flashcards.instance.sets().put(set)
            item.icon = if (set.isFavorite) ContextCompat.getDrawable(this, R.drawable.ic_star_black) else
                ContextCompat.getDrawable(this, R.drawable.ic_star_empty_black)
            return true
        }

        if (item?.itemId == R.id.action_color) {
            val preset = if (!set.color.isBlank()) set.color.toColor() else ContextCompat.getColor(this, R.color.c1)
            SimpleColorDialog()
                    .title(R.string.set_color)
                    .colors(Extensions.setColors())
                    .colorPreset(preset)
                    .cancelable(false)
                    .show(this, DIALOG_SET_COLOR)
            return true
        }

        if (item?.itemId == R.id.action_delete) {
            set.isTrash = true
            Flashcards.instance.sets().put(set)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResult(dialogTag: String, which: Int, extras: Bundle): Boolean {

        if (which == 0) return true

        if (dialogTag == DIALOG_SET_COLOR) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            set.color = hexColor
            set.lastEdited = System.currentTimeMillis()
            Flashcards.instance.sets().put(set)
            // update ui
            toolbar.setBackgroundColor(color)
            changeStatusBarColor(color)
        }

        if (dialogTag == DIALOG_SET_NAME) {
            val title = extras.getString(SimpleInputDialog.TEXT)
            set.title = title
            set.lastEdited = System.currentTimeMillis()
            Flashcards.instance.sets().put(set)
            // update ui
            toolbar.title = title
        }

        // card front color set
        if (dialogTag == DIALOG_SET_CARD_COLOR_FRONT) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            setController.list[viewPager.currentItem].setCardColor(hexColor)
        }

        // card back color set
        if (dialogTag == DIALOG_SET_CARD_COLOR_BACK) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            setController.list[viewPager.currentItem].setCardColor(hexColor, true)
        }

        // card front text color
        if (dialogTag == DIALOG_SET_TEXT_COLOR_FRONT) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            setController.list[viewPager.currentItem].setTextColor(hexColor)
        }

        // card back text color
        if (dialogTag == DIALOG_SET_TEXT_COLOR_BACK) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            setController.list[viewPager.currentItem].setTextColor(hexColor, true)
        }

        return true
    }

    fun changeStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            Extensions.colorDarker(color)?.let {
                window.statusBarColor = it
            }
        }
    }

    override fun onPause() {
        set.count = setController.list.size
        Flashcards.instance.sets().put(set)
        super.onPause()
    }

    override fun onDestroy() {
        set.count = setController.list.size
        Flashcards.instance.sets().put(set)
        super.onDestroy()
    }

}
