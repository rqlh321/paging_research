package com.example.paging_reserch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.paging_reserch.R

class MessageAdapter(
    private val onBindCallBack: (Message) -> Unit
) : PagingDataAdapter<Message, MainViewHolder>(MessageDiffCallback()) {

    override fun onBindViewHolder(
        holder: MainViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false),
        onBindCallBack
    )

}