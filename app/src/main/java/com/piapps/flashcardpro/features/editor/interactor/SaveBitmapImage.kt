package com.piapps.flashcardpro.features.editor.interactor

import android.content.Context
import android.graphics.Bitmap
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.extension.doAsync
import com.piapps.flashcardpro.core.extension.uiThread
import com.piapps.flashcardpro.core.functional.Either
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-11-17 at 01:42.
 */

class SaveBitmapImage
@Inject constructor(private val context: Context) {
    operator fun invoke(bitmap: Bitmap, onResult: (Either<Failure, String>) -> Unit = {}) {
        doAsync {
            val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")

            var saved = false
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                saved = true
            } catch (e: Exception) {
                e.printStackTrace()
                saved = false
            }
            uiThread {
                if (saved)
                    onResult(Either.Right(file.path))
                else
                    onResult(Either.Left(Failure.SaveFileError))
            }
        }
    }
}