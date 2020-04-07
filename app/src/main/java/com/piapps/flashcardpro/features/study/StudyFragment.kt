package com.piapps.flashcardpro.features.study

import android.content.Context
import android.os.Bundle
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
    lateinit var ivFlip: AppCompatImageView
    lateinit var ivNext: AppCompatImageView
    lateinit var ivShuffle: AppCompatImageView
    lateinit var tvCurrentCard: TextView

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

        ivFlip.setOnClickListener {
            flip()
        }

        ivShuffle.setOnClickListener {
            shuffle()
        }
    }

    override fun resume() {
        if (adapter.list.isEmpty())
            presenter.loadCards()
    }

    fun flip() {
        val current = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (current < 0 || current >= adapter.list.size) return
        val vh = rv.findViewHolderForAdapterPosition(current)
        (vh as CardsAdapter.ViewHolder).flip()
    }

    fun scrollNext() {
        val current = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (current + 1 == adapter.list.size){
            rv.scrollToPosition(0)
            tvCurrentCard.text = "${1} / ${adapter.list.size}"
            return
        }
        rv.smoothScrollToPosition(current + 1)
    }

    fun scrollPrevious() {
        val current = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (current - 1 == -1){
            rv.scrollToPosition(adapter.list.size - 1)
            tvCurrentCard.text = "${adapter.list.size} / ${adapter.list.size}"
            return
        }
        rv.smoothScrollToPosition(current - 1)
    }

    fun shuffle() {
        if (adapter.list.isEmpty()) return // BUG FIX: random from = 0 until 0 throws IllegalArgumentException
        val current = layoutManager.findFirstCompletelyVisibleItemPosition()
        val random = presenter.random(adapter.list.size, current)
        rv.scrollToPosition(random)
        tvCurrentCard.text = "${random + 1} / ${adapter.list.size}"
    }

    override fun setSetColor(color: String) {
        adapter.defaultColor = color
    }

    override fun showCards(cards: List<CardDb>) {
        adapter.list.clear()
        adapter.list.addAll(cards)
        adapter.notifyDataSetChanged()
    }

    override fun setOnCardScrolled() {
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val card = adapter.list.getOrNull(pos)
        if (card == null) return
        tvCurrentCard.text = "${pos + 1} / ${adapter.list.size}"
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

}