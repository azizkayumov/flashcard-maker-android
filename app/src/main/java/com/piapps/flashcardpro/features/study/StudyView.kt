package com.piapps.flashcardpro.features.study

import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.platform.BaseView

/**
 * Created by abduaziz on 2019-10-29 at 13:05.
 */

interface StudyView : BaseView {

    fun setSetColor(color: String)
    fun showCards(cards: List<CardDb>)
    fun showCurrentCardPosition()

}