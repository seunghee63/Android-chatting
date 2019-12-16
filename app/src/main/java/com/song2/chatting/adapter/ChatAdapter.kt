package com.song2.chatting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song2.chatting.R
import com.song2.chatting.data.ChatData
class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<ChatData>()

    override fun getItemViewType(position: Int): Int {
        val chatMessage = data[position]

        return if (chatMessage.id=="me") {
            0
        } else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        lateinit var viewHolder: RecyclerView.ViewHolder

        when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_chat_my_item, parent, false)

                return ChatMyViewHolder(view)

            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_chat_your_item, parent, false)

                return ChatYourViewHolder(view)
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val chatMessage = data[position]

        if (chatMessage.id=="me") {
            val holderMyViewHolder: ChatMyViewHolder = holder as ChatMyViewHolder
            holderMyViewHolder.onBind(data[position])

        } else {
            val holderYourViewHolder: ChatYourViewHolder = holder as ChatYourViewHolder
            holderYourViewHolder.onBind(data[position])
        }
    }

    fun addItem(item: ChatData) {
        data.add(item)
    }

}