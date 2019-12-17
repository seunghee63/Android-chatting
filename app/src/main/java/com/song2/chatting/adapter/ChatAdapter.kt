package com.song2.chatting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song2.chatting.R
import com.song2.chatting.data.ChatData
import com.song2.chatting.databinding.RecyclerChatMyItemBinding
import com.song2.chatting.databinding.RecyclerChatYourItemBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ChatMyViewHolder(val binding: RecyclerChatMyItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ChatYourViewHolder(val binding: RecyclerChatYourItemBinding) :
        RecyclerView.ViewHolder(binding.root)

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

        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_chat_my_item, parent, false)

                ChatMyViewHolder(RecyclerChatMyItemBinding.bind(view))
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_chat_your_item, parent, false)

                ChatYourViewHolder(RecyclerChatYourItemBinding.bind(view))
            }
            else ->{
                viewHolder
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder){
            is ChatMyViewHolder -> {
                holder.binding.chatData = data[position]
            }
            is ChatYourViewHolder -> {
                holder.binding.chatData = data[position]
            }
        }

/*        if (chatMessage.id=="me") {
            val holderMyViewHolder: ChatMyViewHolder = holder as ChatMyViewHolder
            holderMyViewHolder.binding.chatData = data[position]

        } else {
            val holderYourViewHolder: ChatYourViewHolder = holder as ChatYourViewHolder
            holderYourViewHolder.binding.chatData = data[position]
        }*/
    }

    fun addItem(item: ChatData) {
        data.add(item)
    }

}