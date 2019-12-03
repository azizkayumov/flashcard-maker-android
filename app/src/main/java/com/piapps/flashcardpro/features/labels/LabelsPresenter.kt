package com.piapps.flashcardpro.features.labels

import com.piapps.flashcardpro.core.db.tables.LabelDb
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.features.labels.interactor.DeleteLabel
import com.piapps.flashcardpro.features.labels.interactor.GetLabels
import com.piapps.flashcardpro.features.labels.interactor.SaveLabel
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-26 at 21:47.
 */

class LabelsPresenter(var view: LabelsView?) : BasePresenter(view) {

    @Inject
    lateinit var getLabels: GetLabels

    @Inject
    lateinit var saveLabel: SaveLabel

    @Inject
    lateinit var deleteLabel: DeleteLabel

    fun loadLabels() {
        getLabels {
            view?.showLabels(it)
        }
    }

    fun checkSaveLabel(label: LabelDb) {
        saveLabel(label) {
            it.callEither(::handleFailure, ::handleSuccess)
        }
    }

    fun handleSuccess(label: LabelDb) {
        view?.showLabel(label)
    }

    fun deleteLabelOffline(l: LabelDb) {
        deleteLabel(l)
    }
}