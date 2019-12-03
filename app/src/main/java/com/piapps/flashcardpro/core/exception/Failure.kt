package com.piapps.flashcardpro.core.exception

/**
 * Created by abduaziz on 2019-09-22 at 00:08.
 */

sealed class Failure {
    object NetworkConnection : Failure()
    object AppInternalError : Failure()
    object ServerError : Failure()

    object SetNotFound: Failure()
    object LabelMustBeUnique: Failure()

    object SetWithNoCards: Failure()
    object CSVExportError: Failure()

    object FileIsNotCSV: Failure()
    object CSVImportError: Failure()

    object NoStatsFound: Failure()
    object SaveFileError: Failure()

    object XLSExportError: Failure()
}