package com.piapps.flashcardpro.features.dialog

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.widget.TextView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.platform.BaseFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.imageResource

/**
 * Created by abduaziz on 2019-11-17 at 10:16.
 */

class DialogFragment : BaseFragment() {

    companion object {
        fun dialog(image: Int,
                   message: Int,
                   left: Int = R.string.cancel,
                   right: Int = R.string.ok,
                   tag: Int = 0): DialogFragment {
            return DialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("tag", tag)
                    putInt("image", image)
                    putInt("message", message)
                    putInt("left", left)
                    putInt("right", right)
                }
            }
        }
    }

    override fun create() {
        enterAnimation = ENTER_FADEIN
        exitAnimation = EXIT_FADEOUT
        super.create()
    }

    lateinit var iv: AppCompatImageView
    lateinit var tvMessage: TextView
    lateinit var tvOk: TextView

    override fun createView(context: Context): View? {
        return DialogUI().createView(AnkoContext.create(context, this))
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        view?.setOnClickListener {
            close()
        }

        val tag = args?.getInt("tag", 0) ?: 0

        iv.imageResource = args?.getInt("image", R.drawable.ic_check) ?: R.drawable.ic_check
        tvMessage.text = ctx.getLocalizedString(args?.getInt("message", R.string.done) ?: R.string.done)
        tvOk.text = ctx.getLocalizedString(args?.getInt("right", R.string.ok) ?: R.string.ok)

        tvOk.setOnClickListener {
            onDialogListener?.onDialogPositiveClick(tag)
            close()
        }
    }

    var onDialogListener: OnDialogListener? = null
    interface OnDialogListener{
        fun onDialogPositiveClick(tag: Int)
        fun onDialogNegativeClick(tag: Int)
    }
}