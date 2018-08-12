package com.piapps.flashcardpro.util

import android.content.pm.PackageManager
import android.os.Environment
import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Card
import com.piapps.flashcardpro.model.Card_
import com.piapps.flashcardpro.model.Set
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Created by abduaziz on 8/12/18.
 */
object CSVUtils {

    val WRITE_EXTERNAL_STORAGE = 1995
    val READ_EXTERNAL_STORAGE = 1996

    // ASK WRITE PERMISSION before using this function
    fun exportToCSV(set: Set): String {
        // 1) Find cards from db
        val query = Flashcards.instance.cards().query().equal(Card_.setId, set.id).build()
        val cards = query.find()
        query.close()
        // if no cards are here, return false
        if (cards.isEmpty())
            return ""

        // 2) Create file
        val parentFolder = File("${Environment.getExternalStorageDirectory().absolutePath}/Flashcards/")
        if (!parentFolder.exists()) {
            parentFolder.mkdirs()
        }
        val now = System.currentTimeMillis()
        val unique = "${DateUtils.getStrictDay(now)}_${DateUtils.getHour_Minute(now)}"
        val file = File(parentFolder.path, "${set.title.replace(" ", "_")}_$unique.csv")
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()

        // 3) Write to the file
        var writer: CSVWriter? = null
        try {
            writer = CSVWriter(java.io.FileWriter(file.path, false))
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        cards.forEach {
            writer.writeNext(arrayOf(it.front, it.back))
        }

        // 4) Close the writer
        writer.close()

        return file.name
    }

    // ASK READ PERMISSION before using this function
    fun importFromCSV(file: File): Set? {
        // Pre-validation
        if (!file.name.endsWith(".csv")) return null

        // 1. Create a new set
        val set = Set(System.currentTimeMillis(), file.name)
        set.lastEdited = System.currentTimeMillis()
        set.color = Extensions.color(set.id).toHexColor()
        Flashcards.instance.sets().put(set)

        try {
            val reader = CSVReader(FileReader(file.absolutePath))
            var line = reader.readNext()
            while (line != null) {

                var front = ""
                if (line.isNotEmpty()) {
                    front = line[0]
                }

                var back = ""
                if (line.size > 1) {
                    back = line[1]
                }

                // 2. Create new card for each entry
                val card = Card(System.currentTimeMillis(), set.id)
                card.front = front
                card.back = back
                card.frontColor = set.color
                card.backColor = set.color
                Flashcards.instance.cards().put(card)

                set.count = set.count + 1
                Flashcards.instance.sets().put(set)

                line = reader.readNext()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Flashcards.instance.sets().remove(set.id)
            return null
        }

        return set
    }

}