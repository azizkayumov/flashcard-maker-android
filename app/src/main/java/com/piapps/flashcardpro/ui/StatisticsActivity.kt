package com.piapps.flashcardpro.ui

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.MenuItem
import android.view.WindowManager
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Card
import com.piapps.flashcardpro.model.Card_
import com.piapps.flashcardpro.model.Set
import com.piapps.flashcardpro.ui.controller.SetController
import com.piapps.flashcardpro.util.Extensions
import com.piapps.flashcardpro.util.toColor
import kotlinx.android.synthetic.main.activity_statistics.*

class StatisticsActivity : AppCompatActivity() {

    lateinit var set: Set
    lateinit var setController: SetController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        set = Flashcards.instance.sets().get(intent.getLongExtra("id", 0L))
        supportActionBar?.title = set.title

        if (!set.color.isBlank()) {
            toolbar.setBackgroundColor(set.color.toColor())
            changeStatusBarColor(set.color.toColor())
        } else {
            toolbar.setBackgroundColor(Extensions.color(set.id))
            changeStatusBarColor(Extensions.color(set.id))
        }

        setController = SetController(set.id, supportFragmentManager, false, true)
        viewPager.setPageTransformer(true, SetController.ZoomOutPageTransformer())
        viewPager.adapter = setController

        setController.loadCards()
        textViewCardNumber.text = "${1} / ${set.count}"
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (setController.list.isEmpty()) return
                textViewCardNumber.text = "${position + 1} / ${set.count}"
                val card = setController.list[viewPager.currentItem].card
                updateStats(card)
            }
        })

        val query = Flashcards.instance.cards()
                .query().equal(Card_.setId, set.id)
                .build()
        val card = query.findFirst()
        query.close()
        card?.let {
            updateStats(it)
        }
    }

    fun updateStats(card: Card) {
        textViewStudied.text = "${card.totalStudied}"
        if (card.totalSeen != 0)
            textViewTrueAnswers.text = "${(card.trueAnswers * 100) / card.totalSeen} %"
        else
            textViewTrueAnswers.text = "0 %"
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
