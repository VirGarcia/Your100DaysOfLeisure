package com.example.your100daysofleisure.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.your100daysofleisure.R
import com.example.your100daysofleisure.utils.SessionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val session = SessionManager(this)

        if (session.getUserName() == null) {
            identifierView = findViewById(R.id.identifierView)
            identifierView.visibility = View.VISIBLE

            userNameText = findViewById(R.id.userNameText)
            addButton = findViewById(R.id.addButton)

            addButton.setOnClickListener{
                session.setUserName(userNameText.text.toString())
                identifierView.visibility = View.GONE
            }
        }*/
    }
}