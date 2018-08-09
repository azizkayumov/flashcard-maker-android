package com.piapps.flashcardpro.ui

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Set
import com.piapps.flashcardpro.ui.controller.SetController
import com.piapps.flashcardpro.util.Extensions
import com.piapps.flashcardpro.util.rand
import com.piapps.flashcardpro.util.toColor
import kotlinx.android.synthetic.main.activity_study.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class StudyActivity : AppCompatActivity() {

    lateinit var set: Set
    lateinit var setController: SetController
    var isQuiz = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        isQuiz = intent.getBooleanExtra("isQuiz", false)
        if (isQuiz) {
            fabShuffle.visibility = View.GONE
            fabFlip.visibility = View.GONE
            viewPager.setPagingEnabled(false)
        } else {
            fabTrue.visibility = View.GONE
            fabWrong.visibility = View.GONE
            viewPager.setPagingEnabled(true)
        }

        set = Flashcards.instance.sets().get(intent.getLongExtra("setId", -1L))
        supportActionBar?.title = set.title

        if (!set.color.isBlank()) {
            toolbar.setBackgroundColor(set.color.toColor())
            changeStatusBarColor(set.color.toColor())
        } else {
            toolbar.setBackgroundColor(Extensions.color(set.id))
            changeStatusBarColor(Extensions.color(set.id))
        }

        setController = SetController(set.id, supportFragmentManager, false, false)
        viewPager.setPageTransformer(true, SetController.ZoomOutPageTransformer())
        viewPager.adapter = setController

        setController.loadMoreCards()
        textViewCardNumber.text = "${1} / ${set.count}"
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

        fabShuffle.setOnClickListener {
            val random = Int.rand(0, setController.list.size - 1)
            viewPager.currentItem = random
        }

        fabFlip.setOnClickListener {
            setController.list[viewPager.currentItem].flip()
            val card = setController.list[viewPager.currentItem].card
            card.totalStudied += 1
            Flashcards.instance.cards().put(card)
        }

        fabTrue.setOnClickListener {
            val card = setController.list[viewPager.currentItem].card
            card.trueAnswers += 1
            card.totalSeen += 1
            Flashcards.instance.cards().put(card)
            scrollNext()
        }

        fabWrong.setOnClickListener {
            val card = setController.list[viewPager.currentItem].card
            card.totalSeen += 1
            Flashcards.instance.cards().put(card)
            scrollNext()
        }
    }

    fun scrollNext() {
        val next = viewPager.currentItem + 1
        if (next < set.count) {
            viewPager.currentItem = next
        } else {
            fabWrong.isEnabled = false
            fabTrue.isEnabled = false
            if (isQuiz) {
                val intent = Intent(this, StatisticsActivity::class.java)
                intent.putExtra("id", set.id)
                startActivity(intent)
            }
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
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
}
