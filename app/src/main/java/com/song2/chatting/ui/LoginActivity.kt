package com.song2.chatting.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.song2.chatting.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_loginact_btn.setOnClickListener {
            val nextIntent = Intent(this, ChatActivity::class.java)
            nextIntent.putExtra("nickName",et_loginact_nickname.text.toString())
            startActivity(nextIntent)
        }
    }
}