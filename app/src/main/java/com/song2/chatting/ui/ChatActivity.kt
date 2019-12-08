package com.song2.chatting.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.song2.chatting.R
import com.song2.chatting.adapter.ChatAdapter
import com.song2.chatting.data.ChatData
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private val chatAdapter by lazy { ChatAdapter() }
    private val dataList = arrayListOf<ChatData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        settingChatUi()
    }

    fun settingChatUi(){
        dataList.add(ChatData("me", "chatbot","song"))
        dataList.add(ChatData("you", "chatbot","song"))
        dataList.add(ChatData("you", "chatbot","song"))

        chatAdapter.data = dataList

        rv_chatact_chatlist.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
    }
}
