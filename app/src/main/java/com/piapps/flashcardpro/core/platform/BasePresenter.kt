package com.piapps.flashcardpro.core.platform

import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.exception.Failure

/**
 * Created by abduaziz on 2019-08-19 at 16:39.
 */

open class BasePresenter(var baseView: BaseView?) {
    open fun handleFailure(failure: Failure) {
        when (failure) {
            Failure.NetworkConnection -> baseView?.showToast(R.string.connection_error)
            Failure.AppInternalError -> baseView?.showToast(R.string.app_internal_error)
            Failure.ServerError -> baseView?.showToast(R.string.server_error)
            Failure.LabelMustBeUnique -> baseView?.showToast(R.string.label_must_be_unique)

            Failure.SetWithNoCards -> baseView?.showToast(R.string.set_with_no_cards_error)
            Failure.CSVExportError -> baseView?.showToast(R.string.csv_export_error)
            Failure.FileIsNotCSV -> baseView?.showToast(R.string.file_is_not_csv_error)
            Failure.CSVImportError -> baseView?.showToast(R.string.csv_import_error)

            Failure.NoStatsFound -> baseView?.showToast(R.string.no_stats_found)
            Failure.SaveFileError -> baseView?.showToast(R.string.save_image_error)

            Failure.XLSExportError -> baseView?.showToast(R.string.xls_export_error)
        }
    }

    open fun clear() {
        baseView = null
    }
}