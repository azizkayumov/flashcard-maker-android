package com.piapps.flashcardpro.features.quiz

import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.platform.BaseView

/**
 * Created by abduaziz on 2019-10-29 at 13:14.
 */

interface QuizView : BaseView {

    fun setColors(backgroundColor: String, textColor: String)
    fun showCards(cards: List<CardDb>)
    fun setOnCardScrolled()

}