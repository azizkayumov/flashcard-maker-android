package com.piapps.flashcardpro.features.editor

import android.graphics.Bitmap
import com.piapps.flashcardpro.core.exception.Failure
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.features.editor.interactor.SaveBitmapImage
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-11-17 at 00:59.
 */

class DrawPresenter(var view: DrawView?) : BasePresenter(view) {

    var path = ""

    @Inject
    lateinit var saveBitmapImage: SaveBitmapImage

    fun save(bitmap: Bitmap) {
        saveBitmapImage(bitmap) {
            it.callEither(::handleFailure, ::handleCropImageFile)
        }
    }

    private fun handleCropImageFile(path: String) {
        view?.showDrawingImageFile(path)
    }

    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        view?.exit()
    }

}