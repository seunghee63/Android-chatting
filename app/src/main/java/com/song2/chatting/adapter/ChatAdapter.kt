package com.song2.chatting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song2.chatting.R
import com.song2.chatting.data.ChatData
class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<ChatData>()

    private val MY_CHAT = 0
    private val YOUR_CHAT = 1

    override fun getItemViewType(position: Int): Int {
        val chatMessage = data[position]

        return if (chatMessage.id=="me") {
            MY_CHAT
        } else {
            YOUR_CHAT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        lateinit var viewHolder: RecyclerView.ViewHolder

        when (viewType) {
            MY_CHAT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_chat_my_item, parent, false)

                return ChatMyViewHolder(view)

            }
            YOUR_CHAT -> {
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

        when (holder){
            is ChatMyViewHolder -> {
                //스마트캐스트
                holder.onBind(data[position])
            }
            is ChatYourViewHolder -> {
                holder.onBind(data[position])
            }
        }
    }

    fun addItem(item: ChatData) {
        data.add(item)
    }

}