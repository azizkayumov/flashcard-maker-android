package com.piapps.flashcardpro.features.quiz

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity
import com.piapps.flashcardpro.features.quiz.adapter.CardsAdapter
import com.piapps.flashcardpro.features.stats.StatsFragment
import org.jetbrains.anko.AnkoContext

/**
 * Created by abduaziz on 2019-10-29 at 13:10.
 */

class QuizFragment : BaseFragment(), QuizView {

    companion object {
        fun set(setId: Long): QuizFragment {
            return QuizFragment().apply {
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

    lateinit var presenter: QuizPresenter

    var adapter: CardsAdapter = CardsAdapter()
    var snapHelper = LinearSnapHelper()
    lateinit var layoutManager: LinearLayoutManager

    lateinit var ivClose: AppCompatImageView
    lateinit var rv: RecyclerView
    lateinit var fabOk: FloatingActionButton
    lateinit var fabWrong: FloatingActionButton
    lateinit var tvCurrentCard: TextView

    override fun createView(context: Context): View? {
        return QuizUI().createView(AnkoContext.Companion.create(context, this))
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = QuizPresenter(this)
        appComponent.inject(presenter)

        val id = args?.getLong("id", 1L) ?: 1L
        presenter.loadSetDetails(id)

        fabWrong.setOnClickListener {
            flip()
        }

        fabOk.setOnClickListener {
            scrollNext()
        }
    }

    override fun resume() {
        if (adapter.list.isEmpty())
            presenter.loadCards()
    }

    fun flip() {
        val current = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (current < 0 || current >= adapter.list.size) return

        presenter.setCardNotAnswered(current, adapter.list[current])

        val vh = rv.findViewHolderForAdapterPosition(current)
        (vh as CardsAdapter.ViewHolder).flip()
    }

    fun scrollNext() {
        val current = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (current >= 0 && current < adapter.list.size) {
            presenter.setCardAnswered(current, adapter.list[current])
        }
        if (current + 1 >= adapter.list.size) {
            presenter.saveStats()
            onCardsUpdatedListener?.onCardsRatingUpdated(adapter.list)
            (activity as MainActivity).replaceFragment(StatsFragment.set(presenter.set.id), true)
            return
        }
        rv.smoothScrollToPosition(current + 1)
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
        val pos = layoutManager.findFirstCompletelyVisibleItemPosition()
        val card = adapter.list.getOrNull(pos)
        if (card == null) return
        tvCurrentCard.text = "${pos + 1} / ${adapter.list.size}"
    }

    override fun showToast(res: Int) {
        toast(res)
    }

    override fun removed() {
        presenter.clear()
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.removed()
    }

    var onCardsUpdatedListener: OnCardsUpdatedListener? = null

    interface OnCardsUpdatedListener {
        fun onCardsRatingUpdated(list: List<CardDb>)
    }
}