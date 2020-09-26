package com.piapps.flashcardpro.features.editor

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import com.piapps.flashcardpro.core.platform.BaseFragment
import kotlin.math.max

/**
 * Created by abduaziz on 9/26/20 at 13:02.
 */

class EditFontSizeFragment : BaseFragment() {

    companion object {
        fun withDefault(size: Float): EditFontSizeFragment {
            return EditFontSizeFragment().apply {
                arguments = Bundle().apply {
                    putFloat("default", size)
                }
            }
        }
    }

    override fun create() {
        enterAnimation = ENTER_FROM_BOTTOM
        exitAnimation = EXIT_TO_BOTTOM
        super.create()
    }

    lateinit var tvCurFontSize: TextView
    lateinit var seekBarFont: SeekBar
    lateinit var chbAll: CheckBox

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        view?.setOnClickListener {
            close()
        }

        val default = arguments?.getFloat("default", 28F) ?: 28F
        seekBarFont.progress = max(0, default.toInt() - 14)
        tvCurFontSize.text = default.toInt().toString()

        seekBarFont.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newFontSize = progress.toFloat() + 14F
                onEditFontSizeListener?.onCardTextSizeChanged(newFontSize, chbAll.isChecked)
                tvCurFontSize.text = newFontSize.toInt().toString()
            }
        })

        chbAll.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val newFontSize = seekBarFont.progress.toFloat() + 14F
                onEditFontSizeListener?.onCardTextSizeChanged(newFontSize, chbAll.isChecked)
            }
        }
    }

    var onEditFontSizeListener: OnEditFontSizeListener? = null
    interface OnEditFontSizeListener {
        fun onCardTextSizeChanged(newSize: Float, forAll: Boolean)
    }

}