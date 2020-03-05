package com.piapps.flashcardpro.features.editor.adapter

/**
 * Created by abduaziz on 2019-10-14 at 22:31.
 */

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.load
import com.piapps.flashcardpro.features.editor.adapter.cells.PhotosItemUI

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    var list: ArrayList<Image> = arrayListOf()
    var selectedImage = ""
    private var currentSelectedImage = Image()
    private var currentSelectedPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(PhotosItemUI().createView(parent.context))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var ivMain: ImageView
        var ivSelector: AppCompatImageView

        init {
            ivMain = itemView.findViewById(PhotosItemUI.ivMainId)
            ivSelector = itemView.findViewById(PhotosItemUI.ivSelectorId)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val image = list[adapterPosition]
            image.isSelected = !image.isSelected

            if (!currentSelectedImage.path.equals(image.path)) {
                currentSelectedImage.isSelected = false
                notifyItemChanged(currentSelectedPos)
            }

            if (image.isSelected) {
                ivSelector.setImageResource(R.drawable.ic_check_circle)
                selectedImage = image.path
                currentSelectedImage = image
                currentSelectedPos = adapterPosition
            } else {
                ivSelector.setImageResource(R.drawable.ic_circle_white)
                selectedImage = ""
                currentSelectedImage = Image()
                currentSelectedPos = -1
            }
        }

        fun bind(image: Image) {
            ivMain.load(image.path)
            if (image.isSelected) {
                ivSelector.setImageResource(R.drawable.ic_check_circle)
            } else {
                ivSelector.setImageResource(R.drawable.ic_circle_white)
            }
        }
    }

    class Image(var path: String = "", var isSelected: Boolean = false)

    var onImageSelectedListener: OnImageSelectedListener? = null

    interface OnImageSelectedListener {
        fun onImageSelected(path: String)
    }
}