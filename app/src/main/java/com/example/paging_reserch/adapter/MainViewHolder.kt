package com.example.paging_reserch.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paging_reserch.R

class MainViewHolder(
    view: View,
    private val onBindCallBack: (Message) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val messageTextView: TextView by lazy { itemView.findViewById(R.id.message_text_id) }

    fun bind(message: Message?) {
        if (message == null) {
            messageTextView.text = null
        } else {
            onBindCallBack.invoke(message)
            messageTextView.text = message.text
            messageTextView.setTextColor(if (message.isWatched) Color.BLACK else Color.GREEN)
        }
    }
}