package com.piapps.flashcard.application

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.piapps.flashcard.model.Card
import com.piapps.flashcard.model.Label
import com.piapps.flashcard.model.MyObjectBox
import com.piapps.flashcard.model.Set
import io.objectbox.BoxStore

/**
 * Created by abduaziz on 4/18/18.
 */
class Flashcards : Application() {

    companion object {
        lateinit var instance: Flashcards
    }

    lateinit var db: BoxStore

    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this);
        db = MyObjectBox.builder().androidContext(this).build()
    }

    fun sets() = db.boxFor(Set::class.java)
    fun labels() = db.boxFor(Label::class.java)
    fun cards() = db.boxFor(Card::class.java)
}