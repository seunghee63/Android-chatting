package com.song2.chatting.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.song2.chatting.R
import com.song2.chatting.adapter.ChatAdapter
import com.song2.chatting.data.ChatData
import com.song2.chatting.data.remote.SocketApplication
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONObject

class ChatActivity : AppCompatActivity() {

    private val chatAdapter by lazy { ChatAdapter() }
    private val dataList = arrayListOf<ChatData>()

    lateinit var socket: Socket
    private lateinit var imm : InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        settingChatUi()
        settingSocket()
    }

    fun settingChatUi() {

        dataList.add(ChatData("me", "chatbot", "song","",""))
        dataList.add(ChatData("you", "chatbot", "song","",""))
        dataList.add(ChatData("you", "chatbot", "song","",""))
        dataList.add(ChatData("me", "chatbot", "song","",""))

        chatAdapter.apply {
            data = dataList
        }

        rv_chatact_chatlist.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }

        rl_chatact_sendbtn.setOnClickListener {

            val message : String = et_chatact_input.text.toString()
            val userMessage = JSONObject()

            userMessage.put("name","song")
            userMessage.put("message",message)

            socket.emit("chat-msg",userMessage)

            chatAdapter.addItem(ChatData("me",message,"song","",""))

            et_chatact_input.setText("")
            imm.hideSoftInputFromWindow(et_chatact_input.windowToken, 0)
        }
    }

    fun settingSocket() {

        socket = SocketApplication.get()
        socket.connect()

        socket.on("chat-msg",onMessageReceived)

    }

    private val onConnect = Emitter.Listener {
        //Toast.makeText(applicationContext,"연결",Toast.LENGTH_LONG).show()
        //TODO
    }

    private val onMessageReceived = Emitter.Listener {

        chatAdapter.addItem(ChatData("you",it[0].toString(),"song","",""))
        //Toast.makeText(applicationContext,"메세지 받음",Toast.LENGTH_LONG).show()
        //TODO
    }

}
