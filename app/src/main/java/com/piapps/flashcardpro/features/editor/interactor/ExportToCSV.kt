package com.piapps.flashcardpro.features.editor.interactor

import android.os.Environment
import com.opencsv.CSVWriter
import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.functional.Either
import com.piapps.flashcardpro.core.util.DateUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-31 at 12:49.
 */

class ExportToCSV
@Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(setId: Long, onResult: (Either<Failure, String>) -> Unit = {}) {
        doAsync {
            val set = repository.getSet(setId)
            val cards = repository.getCards(setId)
            var path = ""
            if (set != null && cards.isNotEmpty()) {

                val parentFolder = File("${Environment.getExternalStorageDirectory().absolutePath}/Flashcards/")
                if (!parentFolder.exists()) {
                    parentFolder.mkdirs()
                }
                val now = System.currentTimeMillis()
                val unique = "${DateUtils.getStrictDay(now)}_${DateUtils.getHourMinute(now)}"
                val file = File(parentFolder.path, "${set.title.replace(" ", "_")}_$unique.csv")
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()

                // 3) Write to the file
                var writer: CSVWriter? = null
                try {
                    writer = CSVWriter(FileWriter(file.path, false))
                } catch (e: Exception) {
                    e.printStackTrace()
                    path = ""
                }
                cards.forEach {
                    writer?.writeNext(arrayOf(it.front, it.back))
                }

                // 4) Close the writer
                writer?.close()

                path = file.path
            }
            uiThread {
                if (set == null) onResult(Either.Left(Failure.SetNotFound))
                else if (cards.isEmpty()) onResult(Either.Left(Failure.SetWithNoCards))
                else if (path.isBlank()) onResult(Either.Left(Failure.CSVExportError))
                else onResult(Either.Right(path))
            }
        }
    }
}