package com.piapps.flashcardpro.core.platform.component

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.ellipsize
import com.piapps.flashcardpro.core.extension.setIconColor
import com.piapps.flashcardpro.core.extension.setRippleEffectBorderless
import com.piapps.flashcardpro.core.extension.setStyle
import org.jetbrains.anko.*

class ActionBar(context: Context) : FrameLayout(context) {

    // back button or hamburger icon to open navigation
    var ivControl: AppCompatImageView = AppCompatImageView(context)
    // action bar title
    var tvTitle: TextView = TextView(context)
    // action bar menu
    var ivMenu: AppCompatImageView = AppCompatImageView(context)

    var backIcon: Int = R.drawable.ic_back

    init {
        backgroundColorResource = R.color.colorPrimary
        // title
        tvTitle.setStyle(context, android.R.style.TextAppearance_DeviceDefault_Widget_ActionBar_Title)
        tvTitle.textColorResource = R.color.colorPrimaryText
        val titleLayoutParams = LayoutParams(matchParent, matchParent).apply {
            marginEnd = dip(56)
            marginStart = dip(56)
        }
        tvTitle.layoutParams = titleLayoutParams
        tvTitle.singleLine = true
        tvTitle.lines = 1
        tvTitle.ellipsize(1)
        tvTitle.setPadding(0, 0, dip(16), 0)
        tvTitle.gravity = Gravity.CENTER_VERTICAL
        addView(tvTitle)
    }

    fun setTitle(resource: Int) {
        tvTitle.setText(resource)
    }

    fun setTitle(s: String) {
        tvTitle.text = s
    }

    internal inline fun initMenu(crossinline action: (Float, Float) -> Unit) {
        ivMenu.layoutParams = LayoutParams(dip(56), dip(56)).apply { gravity = Gravity.END }
        ivMenu.setPadding(dip(16), dip(16), dip(16), dip(16))
        ivMenu.setImageResource(R.drawable.ic_menu)
        ivMenu.setRippleEffectBorderless()
        if (ivMenu.parent == null)
            addView(ivMenu)

        ivMenu.setOnClickListener {
            action.invoke(it.x + it.width / 2, it.y + it.height / 2)
        }
    }

    // make it easier to set onBackClicked
    inline fun onBackClick(crossinline action: () -> Unit) {
        ivControl.layoutParams = LayoutParams(dip(56), dip(56))
        ivControl.setPadding(dip(16), dip(16), dip(16), dip(16))
        ivControl.setImageResource(backIcon)
        ivControl.setRippleEffectBorderless()
        addView(ivControl)

        ivControl.setOnClickListener {
            action.invoke()
        }
    }
}