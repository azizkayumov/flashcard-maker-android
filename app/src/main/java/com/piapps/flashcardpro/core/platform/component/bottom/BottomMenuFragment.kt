package com.piapps.flashcardpro.core.platform.component.bottom

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity
import org.jetbrains.anko.AnkoContext

open class BottomMenuFragment(
    var title: String = "",
    var bottomMenu: BottomMenu
) : BaseFragment() {

    override fun create() {
        enterAnimation = ENTER_FROM_BOTTOM
        exitAnimation = EXIT_TO_BOTTOM
        canSwipe = false
        super.create()
    }

    lateinit var tvTitle: TextView
    lateinit var recyclerView: RecyclerView

    var onBottomMenuClickListener: OnBottomMenuClickListener? = null

    override fun createView(context: Context): View? {
        return BottomMenuUI()
            .createView(AnkoContext.create(context, this))
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        (activity as MainActivity).hideSoftInput()

        view?.setOnClickListener {
            onBottomMenuClickListener?.bottomMenuClosed()
            close()
        }

        if (title.isNotBlank()) {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = title
        } else {
            tvTitle.visibility = View.GONE
        }
        recyclerView.adapter = BottomMenuAdapter(
            bottomMenu,
            onBottomMenuClickListener
        )
    }
}