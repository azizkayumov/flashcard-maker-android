package com.piapps.flashcardpro.features.editor

import com.piapps.flashcardpro.core.platform.BaseView

/**
 * Created by abduaziz on 2019-11-17 at 00:59.
 */

interface CropView : BaseView {

    fun showCropImageFile(path: String)
    fun exit()

}