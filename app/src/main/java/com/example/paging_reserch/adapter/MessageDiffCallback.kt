package com.example.paging_reserch.adapter

import androidx.recyclerview.widget.DiffUtil

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
}