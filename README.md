# Android-chatting
안드로이드 채팅 - Socket.io, FCM, Room
</br></br>
#### 1. socket.io를 이용한 안드로이드 채팅 - 단체 카톡
> SocketApplication.kt

```kotlin
class SocketApplication: Application() {

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
}
```
</br>

>ChatActivity.kt

```kotlin
class ChatActivity : AppCompatActivity() {

    private val chatAdapter by lazy { ChatAdapter() }
    private val dataList = arrayListOf<ChatData>()

    private var nickName = ""

    lateinit var socket: Socket
    private lateinit var imm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        nickName = intent.getStringExtra("nickName")
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        settingSocket()
        settingChatUi()
    }

    private fun settingSocket() {

        socket = SocketApplication.get()
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
```
