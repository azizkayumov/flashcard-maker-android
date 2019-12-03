package com.piapps.flashcardpro.features.editor

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity
import org.jetbrains.anko.AnkoContext

/**
 * Created by abduaziz on 2019-10-03 at 21:39.
 */

class EditNameFragment : BaseFragment() {

    companion object {
        fun current(title: String): EditNameFragment {
            return EditNameFragment().apply {
                arguments = Bundle().apply {
                    putString("current", title)
                }
            }
        }
    }

    override fun create() {
        enterAnimation = ENTER_FADEIN
        exitAnimation = EXIT_FADEOUT
        super.create()
    }

    lateinit var et: EditText
    lateinit var tvOk: TextView

    override fun createView(context: Context): View? {
        return EditNameUI().createView(AnkoContext.create(context, this))
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        (activity as MainActivity).showSoftKeyboard(et)

        et.setText(args?.getString("current", "") ?: "")

        view?.setOnClickListener {
            close()
        }

        tvOk.setOnClickListener {
            onEditNameListener?.onEditName(et.text.toString())
            close()
        }
    }

    override fun removed() {
        (activity as MainActivity).hideSoftInput()
        super.removed()
    }

    var onEditNameListener: OnEditNameListener? = null

    interface OnEditNameListener {
        fun onEditName(s: String)
    }

}