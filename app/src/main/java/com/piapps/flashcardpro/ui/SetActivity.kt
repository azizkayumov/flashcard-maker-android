package com.piapps.flashcardpro.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Card
import com.piapps.flashcardpro.model.Card_
import com.piapps.flashcardpro.model.Set
import com.piapps.flashcardpro.ui.controller.SetController
import com.piapps.flashcardpro.ui.fragment.CardFragment
import com.piapps.flashcardpro.util.Extensions
import com.piapps.flashcardpro.util.save
import com.piapps.flashcardpro.util.toColor
import com.piapps.flashcardpro.util.toHexColor
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.color.SimpleColorDialog
import eltos.simpledialogfragment.input.SimpleInputDialog
import kotlinx.android.synthetic.main.activity_set.*
import org.jetbrains.anko.*

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

    val ACTIVITY_CHOOSE_IMAGE = 1995
    lateinit var setController: SetController
    lateinit var bottomSheet: BottomSheetBehavior<View>
    // is user editing the back side of the current flash card?
    var isEditingBack = false

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

        setController = SetController(set.id, supportFragmentManager, true, true)
        viewPager.setPageTransformer(true, SetController.ZoomOutPageTransformer())
        viewPager.adapter = setController

        setController.loadMoreCards()
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position >= setController.count - 1)
                    setController.loadMoreCards()
            }

            override fun onPageSelected(position: Int) {
                textViewCardNumber.text = "${position + 1} / ${set.count}"
            }
        })

        fab.setOnClickListener {
            addNewCard()
        }

        imageViewCardColor.setOnClickListener {
            if (setController.list.isEmpty()) {
                toast(R.string.add_new_card)
                return@setOnClickListener
            }
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
            if (setController.list.isEmpty()) {
                toast(R.string.add_new_card)
                return@setOnClickListener
            }
            val card = setController.list[viewPager.currentItem]
            val tag = if (card.side() == 0) DIALOG_SET_TEXT_COLOR_FRONT else DIALOG_SET_TEXT_COLOR_BACK
            SimpleColorDialog()
                    .title(R.string.card_text_color)
                    .colors(Extensions.setDarkerColors())
                    .allowCustom(true)
                    .cancelable(true)
                    .show(this, tag)
        }

        bottomSheet = BottomSheetBehavior.from(linearLayoutBottomSheet)
        imageViewText.setOnClickListener {
            if (setController.list.isEmpty()) {
                toast(R.string.add_new_card)
                return@setOnClickListener
            }
            isEditingBack = setController.list[viewPager.currentItem].isEditingBack
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            KeyboardUtils.showSoftInput(editText)

            if (isEditingBack) {
                editText.setText(setController.list[viewPager.currentItem].card.back)
                textViewCounter.text = "${setController.list[viewPager.currentItem].card.front.length}/250"
            } else {
                editText.setText(setController.list[viewPager.currentItem].card.front)
                textViewCounter.text = "${setController.list[viewPager.currentItem].card.front.length}/250"
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                textViewCounter.text = "${p0.toString().length}/250"
            }
        })

        imageViewDone.setOnClickListener {
            if (setController.list.isEmpty()) {
                toast(R.string.add_new_card)
                return@setOnClickListener
            }
            setController.list[viewPager.currentItem].setText(editText.text.toString(), isEditingBack)
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            KeyboardUtils.hideSoftInput(editText)

            setLastEdited()
        }

        imageViewCancel.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            KeyboardUtils.hideSoftInput(editText)
        }

        imageViewInsertImage.setOnClickListener {
            if (setController.list.isEmpty()) {
                toast(R.string.add_new_card)
                return@setOnClickListener
            }
            isEditingBack = setController.list[viewPager.currentItem].isEditingBack
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, ACTIVITY_CHOOSE_IMAGE)
        }

        imageViewInsertAudio.setOnClickListener {
            toast("Insert audio Card ${viewPager.currentItem}")
        }

        bottomNavigationViewMain.onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if (item.itemId == R.id.bottom_nav_study) {
                    openStudyActivity(false)
                    return true
                }
                if (item.itemId == R.id.bottom_nav_quiz) {
                    openStudyActivity(true)
                    return true
                }
                return false
            }
        }
    }

    fun openStudyActivity(isQuiz: Boolean) {
        val intent = Intent(this@SetActivity, StudyActivity::class.java)
        intent.putExtra("setId", set.id)
        intent.putExtra("isQuiz", isQuiz)
        startActivity(intent)
    }

    fun addNewCard() {
        linearLayoutCardEditor.visibility = VISIBLE

        setLastEdited(1)

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
        alert {
            messageResource = R.string.are_you_sure_you_want_to_delete_the_card
            yesButton {
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
                    setLastEdited(-1)

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
            noButton {
                it.dismiss()
            }
        }.show()
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

        if (item?.itemId == R.id.action_stats) {
            val intent = Intent(this, StatisticsActivity::class.java)
            intent.putExtra("id", set.id)
            startActivity(intent)
            return true
        }

        if (item?.itemId == R.id.action_delete) {
            alert {
                messageResource = R.string.are_you_sure_you_want_to_delete_the_set
                yesButton {
                    set.isTrash = true
                    Flashcards.instance.sets().put(set)
                    finish()
                }
                noButton {
                    it.dismiss()
                }
            }.show()
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
            setLastEdited()
            // update ui
            toolbar.setBackgroundColor(color)
            changeStatusBarColor(color)
        }

        if (dialogTag == DIALOG_SET_NAME) {
            val title = extras.getString(SimpleInputDialog.TEXT)
            set.title = title
            setLastEdited()
            // update ui
            toolbar.title = title
        }

        // card front color set
        if (dialogTag == DIALOG_SET_CARD_COLOR_FRONT) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            setController.list[viewPager.currentItem].setCardColor(hexColor)
            setLastEdited()
        }

        // card back color set
        if (dialogTag == DIALOG_SET_CARD_COLOR_BACK) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            setController.list[viewPager.currentItem].setCardColor(hexColor, true)
            setLastEdited()
        }

        // card front text color
        if (dialogTag == DIALOG_SET_TEXT_COLOR_FRONT) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            setController.list[viewPager.currentItem].setTextColor(hexColor)
            setLastEdited()
        }

        // card back text color
        if (dialogTag == DIALOG_SET_TEXT_COLOR_BACK) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            setController.list[viewPager.currentItem].setTextColor(hexColor, true)
            setLastEdited()
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == ACTIVITY_CHOOSE_IMAGE) {

            var bitmap: Bitmap? = null
            try {
                bitmap = ImageUtils.getBitmap(contentResolver.openInputStream(data?.data))
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }

            doAsync {
                val path = bitmap.save(set.id, viewPager.currentItem, this@SetActivity)
                uiThread {
                    setController.list[viewPager.currentItem].setImage(path, isEditingBack)
                    setLastEdited()
                }
            }
        }
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

    fun setLastEdited(count: Int = 0) {
        set.count = set.count + count
        set.lastEdited = System.currentTimeMillis()
        Flashcards.instance.sets().put(set)
    }

    override fun onPause() {
        doAsync {
            set.count = Flashcards.instance.cards().find(Card_.setId, set.id).size
            Flashcards.instance.sets().put(set)
        }
        super.onPause()
    }

    override fun onBackPressed() {
        doAsync {
            set.count = Flashcards.instance.cards().find(Card_.setId, set.id).size
            Flashcards.instance.sets().put(set)
        }
        if (bottomSheet.state != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        } else
            super.onBackPressed()
    }

}
