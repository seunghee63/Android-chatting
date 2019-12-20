package com.song2.chatting.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.song2.chatting.R
import com.song2.chatting.adapter.ChatAdapter
import com.song2.chatting.data.ChatData
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*

class ChatRoomActivity : AppCompatActivity() {

    companion object {

        private val BASE_URL = "https://reactsocketiomo.herokuapp.com/"
        private lateinit var socket : Socket
        fun get(): Socket {
            try {
                socket = IO.socket(BASE_URL)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            return socket
        }
    }

    private val chatAdapter by lazy { ChatAdapter() }
    private val dataList = arrayListOf<ChatData>()

    private var nickName = ""

    lateinit var socket: Socket
    private lateinit var imm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        nickName = intent.getStringExtra("nickName")
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        settingSocket()
        settingChatUi()
    }

    private fun settingSocket() {

        socket = get()
        socket.connect()

        socket.on("chat-msg",onMessageReceived)
    }

    private fun settingChatUi() {

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

            userMessage.put("name",nickName)
            userMessage.put("message",message)

            socket.emit("chat-msg",userMessage)

            chatAdapter.addItem(ChatData("me",message,nickName,"",""))
            rv_chatact_chatlist.scrollToPosition(rv_chatact_chatlist.adapter!!.itemCount - 1)

            et_chatact_input.setText("")
            //imm.hideSoftInputFromWindow(et_chatact_input.windowToken, 0)
        }
    }

    private val onMessageReceived = Emitter.Listener {

        val receiveMessage = it[0] as JSONObject

        val tt = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if(receiveMessage.getString("name").toString() != nickName){
                        chatAdapter.addItem(ChatData("you",receiveMessage.getString("message").toString(),receiveMessage.getString("name").toString(),"https://images.otwojob.com/product/x/U/6/xU6PzuxMzIFfSQ9.jpg/o2j/resize/852x622%3E",""))
                        chatAdapter.notifyDataSetChanged()
                        rv_chatact_chatlist.scrollToPosition(rv_chatact_chatlist.adapter!!.itemCount - 1)
                    }
                }
            }
        }

        tt.run()
    }

}
