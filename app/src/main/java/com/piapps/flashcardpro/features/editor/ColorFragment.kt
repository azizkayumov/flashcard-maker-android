package com.piapps.flashcardpro.features.editor

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.editor.adapter.ColorAdapter

/**
 * Created by abduaziz on 2019-10-06 at 19:51.
 */

class ColorFragment : BaseFragment(), ColorAdapter.OnItemClickListener {

    companion object {
        val SET_COLOR = 0
        val CARD_TEXT = 1
        val CARD_COLOR = 2
        val DRAWING_COLOR = 3

        fun selector(type: Int = SET_COLOR): ColorFragment {
            return ColorFragment().apply {
                arguments = Bundle().apply {
                    putInt("type", type)
                }
            }
        }

        fun setColor() = selector(SET_COLOR)
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
    lateinit var rv: RecyclerView

    val colorAdapter = ColorAdapter()
    var type = SET_COLOR

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        view?.setOnClickListener {
            close()
        }
        type = args?.getInt("type", SET_COLOR) ?: SET_COLOR

        tv.text = when (type) {
            SET_COLOR -> ctx.getLocalizedString(R.string.choose_set_color)
            CARD_COLOR -> ctx.getLocalizedString(R.string.choose_card_background_color)
            DRAWING_COLOR -> ctx.getLocalizedString(R.string.choose_drawing_color)
            else -> ctx.getLocalizedString(R.string.choose_card_text_color)
        }

        type = args?.getInt("type", SET_COLOR) ?: SET_COLOR
        when (type) {
            SET_COLOR, CARD_COLOR -> {
                colorAdapter.list.addAll(
                    listOf(
                        ColorAdapter.ColorEntity(R.color.c1),
                        ColorAdapter.ColorEntity(R.color.c2),
                        ColorAdapter.ColorEntity(R.color.c3),
                        ColorAdapter.ColorEntity(R.color.c4),
                        ColorAdapter.ColorEntity(R.color.c5),
                        ColorAdapter.ColorEntity(R.color.c6),
                        ColorAdapter.ColorEntity(R.color.c7),
                        ColorAdapter.ColorEntity(R.color.c8),
                        ColorAdapter.ColorEntity(R.color.c9),
                        ColorAdapter.ColorEntity(R.color.c10),
                        ColorAdapter.ColorEntity(R.color.c11),
                        ColorAdapter.ColorEntity(R.color.c12),
                        ColorAdapter.ColorEntity(R.color.c13),
                        ColorAdapter.ColorEntity(R.color.c14),
                        ColorAdapter.ColorEntity(R.color.c15)
                    )
                )
            }
            CARD_TEXT, DRAWING_COLOR -> {
                colorAdapter.list.addAll(
                    listOf(
                        ColorAdapter.ColorEntity(R.color.md_red_900),
                        ColorAdapter.ColorEntity(R.color.md_pink_900),
                        ColorAdapter.ColorEntity(R.color.md_purple_900),
                        ColorAdapter.ColorEntity(R.color.md_deep_purple_900),
                        ColorAdapter.ColorEntity(R.color.md_indigo_900),
                        ColorAdapter.ColorEntity(R.color.md_blue_900),
                        ColorAdapter.ColorEntity(R.color.md_light_blue_900),
                        ColorAdapter.ColorEntity(R.color.md_teal_900),
                        ColorAdapter.ColorEntity(R.color.md_light_green_900),
                        ColorAdapter.ColorEntity(R.color.md_yellow_900),
                        ColorAdapter.ColorEntity(R.color.md_amber_900),
                        ColorAdapter.ColorEntity(R.color.md_brown_500),
                        ColorAdapter.ColorEntity(R.color.white),
                        ColorAdapter.ColorEntity(R.color.black)
                    )
                )
            }
        }
        colorAdapter.onItemClickListener = this
        colorAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(c: ColorAdapter.ColorEntity) {
        when (type) {
            SET_COLOR -> onColorSelectedListener?.onSetColorSelected(c.color)
            CARD_COLOR -> onColorSelectedListener?.onCardBackgroundColorSelected(c.color)
            CARD_TEXT -> onColorSelectedListener?.onCardTextColorSelected(c.color)
            DRAWING_COLOR -> {
                onDrawingColorSelectedListener?.onDrawingColorSelected(c.color)
                close()
            }
        }
    }

    var onColorSelectedListener: OnColorSelectedListener? = null

    interface OnColorSelectedListener {
        fun onSetColorSelected(color: Int)
        fun onCardTextColorSelected(color: Int)
        fun onCardBackgroundColorSelected(color: Int)
    }

    var onDrawingColorSelectedListener: OnDrawingColorSelectedListener? = null

    interface OnDrawingColorSelectedListener {
        fun onDrawingColorSelected(color: Int)
    }

}