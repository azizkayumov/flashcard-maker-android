package com.piapps.flashcardpro.ui.controller

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Card_
import com.piapps.flashcardpro.ui.fragment.CardFragment


/**
 * Created by abduaziz on 5/6/18.
 */

class SetController(val id: Long, fm: FragmentManager, var isDeletable: Boolean = true, var isFlippable: Boolean = true) : FragmentStatePagerAdapter(fm) {

    val list = arrayListOf<CardFragment>()

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): CardFragment {
        return list[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        val index = list.indexOf(`object`)

        return if (index == -1)
            PagerAdapter.POSITION_NONE
        else
            index

        return super.getItemPosition(`object`)
    }

    fun addFragment(fragment: CardFragment) {
        list.add(fragment)
        notifyDataSetChanged()
    }

    fun deleteFragment(pos: Int) {
        if (pos < 0 || pos >= list.size) return
        list.removeAt(pos)
        notifyDataSetChanged()
    }

    fun loadCards() {
        val query = Flashcards.instance.cards()
                .query().equal(Card_.setId, id)
                .build()
        query.find().forEach {
            addFragment(CardFragment.newInstance(it.id, isDeletable, isFlippable))
        }
        query.close()
    }

    class ZoomOutPageTransformer : ViewPager.PageTransformer {
        private val MIN_SCALE = 0.85f
        private val MIN_ALPHA = 0.5f

        override fun transformPage(view: View, position: Float) {
            val pageWidth = view.getWidth()
            val pageHeight = view.getHeight()

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0F)

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2)
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2)
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor)
                view.setScaleY(scaleFactor)

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA))

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0F)
            }
        }
    }

}