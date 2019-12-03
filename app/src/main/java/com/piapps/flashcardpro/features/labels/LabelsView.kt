package com.piapps.flashcardpro.features.labels

import com.piapps.flashcardpro.core.db.tables.LabelDb
import com.piapps.flashcardpro.core.platform.BaseView

/**
 * Created by abduaziz on 2019-10-26 at 21:47.
 */

interface LabelsView : BaseView {

    fun showLabels(list: List<LabelDb>)
    fun showLabel(label: LabelDb)

}