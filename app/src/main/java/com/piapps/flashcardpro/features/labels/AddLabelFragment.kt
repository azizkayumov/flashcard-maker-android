package com.piapps.flashcardpro.features.labels

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity

/**
 * Created by abduaziz on 2019-10-03 at 21:39.
 */

class AddLabelFragment : BaseFragment() {

    override fun create() {
        enterAnimation = ENTER_FADEIN
        exitAnimation = EXIT_FADEOUT
        super.create()
    }

    lateinit var et: EditText
    lateinit var tvOk: TextView

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        (activity as MainActivity).showSoftKeyboard(et)

        view?.setOnClickListener {
            close()
        }

        tvOk.setOnClickListener {
            onLabelAddedListener?.onEditName(et.text.toString())
            close()
        }
    }

    override fun removed() {
        (activity as MainActivity).hideSoftInput()
        super.removed()
    }

    var onLabelAddedListener: OnLabelAddedListener? = null

    interface OnLabelAddedListener {
        fun onEditName(s: String)
    }

}