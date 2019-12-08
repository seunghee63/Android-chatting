package com.song2.chatting.data.remote

import android.app.Application
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

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