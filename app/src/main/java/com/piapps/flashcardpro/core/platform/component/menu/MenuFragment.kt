package com.piapps.flashcardpro.core.platform.component.menu

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kent.layouts.dip
import com.kent.layouts.matchParent
import com.kent.layouts.wrapContent
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.platform.LONG_ANIMATION

class MenuFragment(var menu: Menu, var x: Float = 0f, var y: Float = 0f) : BaseFragment() {

    private lateinit var cardView: CardView
    private lateinit var rv: RecyclerView
    private lateinit var adapter: MenuAdapter
    var onMenuClickListener: OnMenuClickListener? = null

    override fun create() {
        super.create()
        canSwipe = false
    }

    override fun createView(context: Context): View? {
        val parent = FrameLayout(context)
        parent.layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
        parent.setBackgroundResource(android.R.color.transparent)
        parent.isClickable = true

        cardView = CardView(context)
        cardView.layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent)
        (cardView.layoutParams as FrameLayout.LayoutParams).setMargins(
            context.dip(8),
            context.dip(8),
            context.dip(8),
            context.dip(8)
        )
        (cardView.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.END
        cardView.cardElevation = cardView.dip(4).toFloat()
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, theme.white))

        rv = RecyclerView(context)
        rv.layoutParams = FrameLayout.LayoutParams(context.dip(184), wrapContent)
        rv.layoutManager = LinearLayoutManager(context)
        cardView.addView(rv)

        parent.addView(cardView)
        return parent
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        adapter = MenuAdapter(menu)
        rv.adapter = adapter
        adapter.onMenuClickListener = onMenuClickListener

        val centerX = x.toInt()
        val centerY = y.toInt()

        val startRadius = 0f

        val point = Point()
        activity?.windowManager?.defaultDisplay?.getSize(point)
        val endRadius = Math.hypot(point.x.toDouble(), point.y.toDouble()).toFloat()

        val circularReveal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius)
        } else {
            null
        }

        circularReveal?.interpolator = AccelerateInterpolator()
        circularReveal?.duration = LONG_ANIMATION
        circularReveal?.start()
    }

    override fun removed() {
        val centerX = x.toInt()
        val centerY = y.toInt()

        val point = Point()
        activity?.windowManager?.defaultDisplay?.getSize(point)
        val startRadius = Math.hypot(
            parentView.dip(184).toDouble(), menu.size()
                    * (parentView.dip(48).toDouble())
        ).toFloat()
        val endRadius = 0f

        val circularReveal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(parentView, centerX, centerY, startRadius, endRadius)
        } else {
            null
        }

        circularReveal?.interpolator = DecelerateInterpolator()
        circularReveal?.duration = LONG_ANIMATION
        circularReveal?.start()
    }
}