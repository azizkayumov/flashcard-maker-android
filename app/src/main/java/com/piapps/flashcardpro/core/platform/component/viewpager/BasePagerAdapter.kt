package com.piapps.flashcardpro.core.platform.component.viewpager

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.piapps.flashcardpro.core.platform.BaseFragment

/**
 * Created by abduaziz on 10/3/18 at 2:24 PM.
 */

open class BasePagerAdapter(var activity: Activity, var fragments: List<BaseFragment>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        fragments[position].activity = activity
        fragments[position].ctx = activity

        fragments[position].create()

        val view = fragments[position].createView(activity)
        container.addView(view)
        fragments[position].viewCreated(view, fragments[position].arguments)
        return view!!
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        fragments[position].resume()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        (`object` as? BaseFragment)?.removed()
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Position $position"
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }
}