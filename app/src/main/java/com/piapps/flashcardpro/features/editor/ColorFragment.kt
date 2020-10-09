package com.piapps.flashcardpro.features.editor

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.slider.LightnessSlider
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.platform.BaseFragment

/**
 * Created by abduaziz on 2019-10-06 at 19:51.
 */

class ColorFragment : BaseFragment() {

    companion object {
        val CARD_TEXT = 1
        val CARD_COLOR = 2
        val DRAWING_COLOR = 3

        fun selector(type: Int = CARD_COLOR): ColorFragment {
            return ColorFragment().apply {
                arguments = Bundle().apply {
                    putInt("type", type)
                }
            }
        }

        fun cardBackground() = selector(CARD_COLOR)
        fun cardTextColor() = selector(CARD_TEXT)
        fun drawingColor() = selector(DRAWING_COLOR)
    }

    override fun create() {
        enterAnimation = ENTER_FROM_BOTTOM
        exitAnimation = EXIT_TO_BOTTOM
        super.create()
    }

    lateinit var tv: TextView
    lateinit var colorPicker: ColorPickerView
    lateinit var lightness: LightnessSlider

    var type = CARD_COLOR

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        view?.setOnClickListener {
            close()
        }
        type = args?.getInt("type", CARD_COLOR) ?: CARD_COLOR

        tv.text = when (type) {
            CARD_COLOR -> ctx.getLocalizedString(R.string.choose_card_background_color)
            DRAWING_COLOR -> ctx.getLocalizedString(R.string.choose_drawing_color)
            else -> ctx.getLocalizedString(R.string.choose_card_text_color)
        }

        colorPicker.setLightnessSlider(lightness)
        lightness.setColor(Color.WHITE)
        colorPicker.addOnColorSelectedListener { color ->
            when (type) {
                CARD_COLOR -> onColorSelectedListener?.onCardBackgroundColorSelected(color)
                CARD_TEXT -> onColorSelectedListener?.onCardTextColorSelected(color)
                DRAWING_COLOR -> onDrawingColorSelectedListener?.onDrawingColorSelected(color)
            }
        }
        colorPicker.addOnColorChangedListener { color ->
            when (type) {
                CARD_COLOR -> onColorSelectedListener?.onCardBackgroundColorSelected(color)
                CARD_TEXT -> onColorSelectedListener?.onCardTextColorSelected(color)
                DRAWING_COLOR -> onDrawingColorSelectedListener?.onDrawingColorSelected(color)
            }
        }
    }

    var onColorSelectedListener: OnColorSelectedListener? = null

    interface OnColorSelectedListener {
        fun onCardTextColorSelected(color: Int)
        fun onCardBackgroundColorSelected(color: Int)
    }

    var onDrawingColorSelectedListener: OnDrawingColorSelectedListener? = null

    interface OnDrawingColorSelectedListener {
        fun onDrawingColorSelected(color: Int)
    }

}