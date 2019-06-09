package com.piapps.flashcardpro.ui.controller

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.model.Card

/**
 * Created by abduaziz on 6/9/19 at 3:21 PM.
 */

class SetPreviewAdapter : RecyclerView.Adapter<SetPreviewAdapter.ViewHolder>() {

    val list = listOf<Card>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.fragment_card, p0))
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(card: Card){

        }

    }
}