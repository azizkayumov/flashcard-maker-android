package com.piapps.flashcardpro.ui.controller

import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Set
import com.piapps.flashcardpro.ui.MainActivity
import com.piapps.flashcardpro.ui.SetActivity
import com.piapps.flashcardpro.util.DateUtils
import com.piapps.flashcardpro.util.Extensions
import com.piapps.flashcardpro.util.toColor
import org.jetbrains.anko.alert

/**
 * Created by abduaziz on 4/19/18.
 */

class SetsController(var list: ArrayList<Set>) : RecyclerView.Adapter<SetsController.SetViewHolder>() {

    enum class SORTING_ORDER {
        CREATED_TIME, LAST_EDITED
    }

    var isTrash = false
    var sortingOrder: SORTING_ORDER = SORTING_ORDER.LAST_EDITED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        return SetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_set, parent, false))
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(position, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var textViewDate: TextView
        var textViewTime: TextView
        var cardView: CardView
        var textViewTitle: TextView
        var textViewCount: TextView

        init {
            itemView.setOnClickListener(this)
            textViewDate = itemView.findViewById(R.id.textViewDate)
            textViewTime = itemView.findViewById(R.id.textViewTime)
            cardView = itemView.findViewById(R.id.cardView)
            textViewTitle = itemView.findViewById(R.id.textViewSetTitle)
            textViewCount = itemView.findViewById(R.id.textViewSetCount)
        }

        fun bind(position: Int, set: Set) {

            var sortDate = set.lastEdited
            if (sortingOrder == SORTING_ORDER.CREATED_TIME) {
                sortDate = set.id
            }

            textViewDate.visibility = VISIBLE
            if (position - 1 >= 0) {

                var prevDate = list[position - 1].lastEdited
                if (sortingOrder == SORTING_ORDER.CREATED_TIME) {
                    prevDate = list[position - 1].id
                }

                if (DateUtils.getDay(prevDate).equals(DateUtils.getDay(sortDate))) {
                    textViewDate.visibility = GONE
                }
            }

            textViewDate.text = "${DateUtils.getDay(sortDate)}"
            textViewTime.text = "${DateUtils.getHourMinute(sortDate)}"

            textViewTitle.text = set.title
            textViewCount.text = "${set.count} cards"

            //todo: set the set color if it exists
            if (set.color.isBlank())
                cardView.setCardBackgroundColor(Extensions.color(set.id))
            else
                cardView.setCardBackgroundColor(set.color.toColor())
        }

        override fun onClick(p0: View?) {
            if (!isTrash) {
                val intent = Intent(itemView.context, SetActivity::class.java)
                intent.putExtra("id", list[adapterPosition].id)
                itemView.context.startActivity(intent)
            }else{
                MainActivity.instance.alert() {
                    message = MainActivity.instance.getString(R.string.put_back)
                    positiveButton(MainActivity.instance.getString(R.string.yes)){
                        val set = list[adapterPosition]
                        set.isTrash = false
                        Flashcards.instance.sets().put(set)
                        list.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        MainActivity.instance.update()
                    }
                    negativeButton(MainActivity.instance.getString(R.string.cancel)){
                        it.dismiss()
                    }
                }.show()
            }
        }
    }
}