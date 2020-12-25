package com.piapps.flashcardpro.features.main

import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.platform.BaseView
import com.piapps.flashcardpro.features.main.entity.NavView

/**
 * Created by abduaziz on 2019-09-25 at 22:17.
 */

interface MainView : BaseView {

    fun setTitle(res: Int)
    fun setTitle(s: String)

    fun showLabels(list: List<NavView>)

    fun showSets(list: List<SetDb>)
    fun showSet(set: SetDb)
    fun onSetPutBack(set: SetDb)
    fun removeSets()

    fun setCount(): Int
    fun showNothingFound(res: Int)
    fun hideNothingFound()

}