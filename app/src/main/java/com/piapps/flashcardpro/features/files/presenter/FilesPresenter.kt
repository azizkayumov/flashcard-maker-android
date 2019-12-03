package uz.yuridik.client.features.files.presenter

import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.features.files.view.FilesView

class FilesPresenter(view: FilesView) {
    init {
        view.setTitle(R.string.select_csv_files)
    }
}