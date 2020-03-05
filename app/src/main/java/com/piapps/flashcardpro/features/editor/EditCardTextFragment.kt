package com.piapps.flashcardpro.features.editor

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageView
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.platform.component.bottom.BottomMenuFragment
import com.piapps.flashcardpro.features.MainActivity

/**
 * Created by abduaziz on 2019-10-09 at 23:55.
 */

class EditCardTextFragment : BaseFragment() {

    companion object {
        fun withText(text: String): EditCardTextFragment {
            return EditCardTextFragment().apply {
                arguments = Bundle().apply {
                    putString("text", text)
                }
            }
        }
    }

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        super.create()
    }

    lateinit var editText: EditText
    lateinit var ivOk: AppCompatImageView

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        (activity as MainActivity).removeFragmentType(BottomMenuFragment::class.java)
        val text = args?.getString("text", "") ?: ""
        editText.setText(text)

        ivOk.setOnClickListener {
            onCardTextEditedListener?.onCardTextEdited(editText.text.toString())
            close()
        }
    }

    override fun resume() {
        super.resume()
        (activity as MainActivity).showSoftKeyboard(editText)
    }

    var onCardTextEditedListener: OnCardTextEditedListener? = null

    interface OnCardTextEditedListener {
        fun onCardTextEdited(text: String)
    }
}