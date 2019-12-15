# Android-chatting
안드로이드 채팅 - Socket.io, FCM, Room
</br></br>
#### 1. socket.io를 이용한 안드로이드 채팅 - 단체 카톡
> SocketApplication.kt

```
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

```
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

        dataList.add(ChatData("you", "hi~~~", "song","https://images.otwojob.com/product/x/U/6/xU6PzuxMzIFfSQ9.jpg/o2j/resize/852x622%3E",""))

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

    private val onMessageReceived = Emitter.Listener {

        val receiveMessage = it.get(0) as JSONObject

        if(receiveMessage.getString("name").toString() != "song"){
            chatAdapter.addItem(ChatData("you",receiveMessage.getString("message").toString(),receiveMessage.getString("name").toString(),"https://images.otwojob.com/product/x/U/6/xU6PzuxMzIFfSQ9.jpg/o2j/resize/852x622%3E",""))
        }
    }

}

```
