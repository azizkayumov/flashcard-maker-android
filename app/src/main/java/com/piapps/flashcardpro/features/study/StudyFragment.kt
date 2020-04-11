package com.piapps.flashcardpro.features.study

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.study.adapter.CardsAdapter

/**
 * Created by abduaziz on 2019-10-29 at 13:02.
 */

class StudyFragment : BaseFragment(), StudyView {

    companion object {
        fun set(setId: Long): StudyFragment {
            return StudyFragment().apply {
                arguments = Bundle().apply {
                    putLong("id", setId)
                }
            }
        }
    }

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        canSwipe = false
        super.create()
    }

    lateinit var presenter: StudyPresenter
    var adapter: CardsAdapter = CardsAdapter()
    var snapHelper = LinearSnapHelper()
    lateinit var layoutManager: LinearLayoutManager

    lateinit var ivClose: AppCompatImageView
    lateinit var rv: RecyclerView
    lateinit var ivPrevious: AppCompatImageView
    lateinit var ivNext: AppCompatImageView
    lateinit var ivShuffle: AppCompatImageView
    lateinit var tvCurrentCard: TextView

    var currentCardPosition = 0
    lateinit var font: Typeface

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        presenter = StudyPresenter(this)
        appComponent.inject(presenter)

        val id = args?.getLong("id", 1L) ?: 1L
        presenter.loadSetDetails(id)

        ivPrevious.setOnClickListener {
            scrollPrevious()
        }

        ivNext.setOnClickListener {
            scrollNext()
        }

        ivShuffle.setOnClickListener {
            shuffle()
        }

        font = Typeface.createFromAsset(ctx.assets, "SansForgetica-Regular.otf")
        adapter.typeface = font
    }

    override fun resume() {
        if (adapter.list.isEmpty())
            presenter.loadCards()
    }

    fun scrollNext() {
        // if the number of cards is 1, no need to scroll
        if (adapter.list.size == 1) return
        currentCardPosition += 1
        validateCurrentCardPosition()
        rv.smoothScrollToPosition(currentCardPosition)
        showCurrentCardPosition()
    }

    fun scrollPrevious() {
        // if the number of cards is 1, no need to scroll
        if (adapter.list.size == 1) return
        currentCardPosition -= 1
        validateCurrentCardPosition()
        rv.smoothScrollToPosition(currentCardPosition)
        showCurrentCardPosition()
    }

    fun shuffle() {
        if (adapter.list.isEmpty()) return // BUG FIX: random from = 0 until 0 throws IllegalArgumentException
        val until = if (Int.MAX_VALUE - adapter.list.size < currentCardPosition) currentCardPosition else currentCardPosition + adapter.list.size
        currentCardPosition = presenter.random(currentCardPosition, until, currentCardPosition)
        rv.scrollToPosition(currentCardPosition)
        showCurrentCardPosition()
    }

    override fun setSetColor(color: String) {
        adapter.defaultColor = color
    }

    override fun showCards(cards: List<CardDb>) {
        adapter.list.clear()
        adapter.list.addAll(cards)
        adapter.notifyDataSetChanged()

        // Initialising currentCardPosition for the first time:
        // 1) Init currentCardPosition as:
        //          currentCardPosition = Int.MAX_VALUE / 2
        // 2) Substract Int.MAX_VALUE / 2 mod (number of cards) to currentCardPosition so that:
        //          - number of cards is a divisor of currentCardPosition
        //    P.S. if the number of cards is a divisor of currentCardPosition, the first card of the set
        //         is displayed first
        currentCardPosition = Int.MAX_VALUE / 2 - Int.MAX_VALUE / 2 % cards.size
        rv.scrollToPosition(currentCardPosition)
        showCurrentCardPosition()
    }

    override fun showCurrentCardPosition() {
        val pos = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (pos != -1)
            currentCardPosition = pos
        tvCurrentCard.text = "${currentCardPosition % adapter.list.size + 1} / ${adapter.list.size}"
    }

    override fun showToast(res: Int) {
        toast(res)
    }

    override fun removed() {
        onSetStudyDurationUpdatedListener?.onSetStudiedDuration(System.currentTimeMillis() - presenter.studyStart)
        presenter.clear()
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.removed()
    }

    var onSetStudyDurationUpdatedListener: OnSetStudyDurationUpdatedListener? = null

    interface OnSetStudyDurationUpdatedListener {
        fun onSetStudiedDuration(duration: Long)
    }

    fun validateCurrentCardPosition() {
        // Noone is gonna scroll Int.MAX_VALUE / 2 times (billion times)
        // Validate against if some dude scrolls billion times (happens with very low probability)
        if (currentCardPosition == Int.MAX_VALUE)
            currentCardPosition -= 1
        else if (currentCardPosition == -1)
            currentCardPosition = 0
    }
}