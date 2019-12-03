package com.piapps.flashcardpro.features.files.view

import java.io.File

interface OnFilesSelectedListener {
    fun onFilesSelected(files: ArrayList<File>)
}