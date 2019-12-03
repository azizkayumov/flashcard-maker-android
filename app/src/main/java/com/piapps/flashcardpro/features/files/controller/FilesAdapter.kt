package com.piapps.flashcardpro.features.files.controller

import android.os.Environment
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.setIconColor
import com.piapps.flashcardpro.core.util.FileUtils
import com.piapps.flashcardpro.core.util.FileUtils.getFileIcon
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File

class FilesAdapter : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {
    var list: ArrayList<File> = arrayListOf()
    var isParent: ArrayList<Boolean> = arrayListOf()
    var selectedFiles: ArrayList<File> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(FilesItemUI().createView(AnkoContext.Companion.create(parent.context, parent)))

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position), isParent.get(position))
    }

    // bottom sheet adapter updates
    fun fetchFiles(file: File) {
        if (!file.canRead()) {
            return
        }

        if (!file.isDirectory) {
            //ToastUtils.showShortToast("Send file = ${file.name}")
            return
        }

        if (file.isDirectory && file.listFiles().size == 0) {
            onItemClickListener?.showToast(R.string.folder_is_empty)
            return
        }

        list.clear()
        isParent.clear()

        doAsync {
            val childFiles = file.listFiles()
            val dirs = childFiles.filter { it.isDirectory && it.canRead() }.sortedBy { it.name }
            val notdirs = childFiles.filter { !it.isDirectory && it.canRead() }.sortedBy { it.name }
            if (file != Environment.getExternalStorageDirectory()) {
                list.add(file.parentFile)
                isParent.add(true)
            }
            dirs.forEach {
                if (!it.name.startsWith(".")) {
                    list.add(it)
                    isParent.add(false)
                }
            }
            notdirs.forEach {
                list.add(it)
                isParent.add(false)
            }


            uiThread {
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvName: TextView

        var tvDescription: TextView
        var ivIcon: AppCompatImageView

        init {
            tvName = itemView.findViewById(FilesItemUI.tvNameId)
            tvDescription = itemView.findViewById(FilesItemUI.tvDescriptionId)
            ivIcon = itemView.findViewById(FilesItemUI.ivIconId)
            itemView.setOnClickListener(this)
            setFabVisibility()
        }

        fun bind(file: File, isParent: Boolean) {
            val filename = file.name

            if (file == Environment.getExternalStorageDirectory())
                tvName.text = "..."
            else {
                if (isParent)
                    tvName.text = "..."
                else
                    tvName.text = filename

            }
            //if the current file is dir, show path, otherwise show its size
            if (file.isDirectory) {
                if (isParent) {
                    tvDescription.text = file.path
                } else {
                    tvDescription.text = itemView.context.getLocalizedString(R.string.folder)
                }
                ivIcon.setImageResource(R.drawable.extension_folder)
                ivIcon.clearColorFilter()
            } else {
                tvDescription.text = FileUtils.humanReadableByteCount(file.length())
                if (!selectedFiles.contains(file)) {
                    ivIcon.setImageResource(getFileIcon(file))
                    ivIcon.clearColorFilter()
                } else {
                    ivIcon.setImageResource(R.drawable.ic_check_circle)
                    ivIcon.setIconColor(itemView.context, R.color.colorAccent)
                }
            }
        }

        override fun onClick(p0: View?) {
            if (list[adapterPosition].isDirectory) {
                fetchFiles(list[adapterPosition])
                selectedFiles.clear()
            } else {
                if (selectedFiles.contains(list[adapterPosition])) {
                    selectedFiles.remove(list[adapterPosition])
                    ivIcon.setImageResource(getFileIcon(list[adapterPosition]))
                    ivIcon.clearColorFilter()
                } else {
                    selectedFiles.add(list[adapterPosition])
                    ivIcon.setImageResource(R.drawable.ic_check_circle)
                    ivIcon.setIconColor(itemView.context,R.color.colorAccent)
                }
                setFabVisibility()
            }
        }
    }

    fun setFabVisibility() {
        if (selectedFiles.isEmpty()) {
            onItemClickListener?.fabVisibility(false)
        } else {
            onItemClickListener?.fabVisibility(true)
        }
    }

    var onItemClickListener: OnItemClickListener? = null
    fun addItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun showToast(message: Int)
        fun fabVisibility(visible: Boolean)
    }


}
