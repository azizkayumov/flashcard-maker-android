package com.piapps.flashcardpro.features.editor.interactor

import com.opencsv.CSVReader
import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.extension.doAsync
import com.piapps.flashcardpro.core.extension.uiThread
import com.piapps.flashcardpro.core.functional.Either
import java.io.File
import java.io.FileReader
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-31 at 13:07.
 */

class ImportFromCSV
@Inject constructor(private val repository: DatabaseRepository) {

    operator fun invoke(set: SetDb, file: File, onResult: (Either<Failure, List<CardDb>>) -> Unit = {}) {
        if (!file.name.endsWith(".csv")) {
            onResult(Either.Left(Failure.FileIsNotCSV))
            return
        }
        doAsync {
            val cards = arrayListOf<CardDb>()
            val id = System.currentTimeMillis()
            try {
                val reader = CSVReader(FileReader(file.absolutePath))
                var line = reader.readNext()
                var index = 0
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
                    val card = CardDb(id + index, set.id)
                    card.front = front
                    card.back = back
                    cards.add(card)

                    line = reader.readNext()
                    index++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            uiThread {
                if (cards.isEmpty()) onResult(Either.Left(Failure.CSVImportError))
                else onResult(Either.Right(cards))
            }
        }
    }
}