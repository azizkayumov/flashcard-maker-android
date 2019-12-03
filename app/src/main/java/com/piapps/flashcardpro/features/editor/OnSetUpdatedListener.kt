package com.piapps.flashcardpro.features.editor

import com.piapps.flashcardpro.core.db.tables.SetDb

/**
 * Created by abduaziz on 2019-10-04 at 20:36.
 */

interface OnSetUpdatedListener {
    fun onSetUpdated(set: SetDb)
    fun onSetMovedToTrash(set: SetDb)
}