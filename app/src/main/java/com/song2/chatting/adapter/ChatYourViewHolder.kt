package com.song2.chatting.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.song2.chatting.R
import com.song2.chatting.data.ChatData

class ChatYourViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val profileImage: ImageView = view.findViewById(R.id.iv_yourchatitem_profile)
    val user: TextView = view.findViewById(R.id.tc_yourchatitem_user)
    val message: TextView = view.findViewById(R.id.tv_yourchatitem_message)

    fun onBind(chatData: ChatData) {
        message.text = chatData.message
        user.text = chatData.user

        Glide.with(itemView.context).load(chatData.profile)
            .transform(RoundedCorners(180))
            .transition(DrawableTransitionOptions.withCrossFade()) to profileImage
    }
}