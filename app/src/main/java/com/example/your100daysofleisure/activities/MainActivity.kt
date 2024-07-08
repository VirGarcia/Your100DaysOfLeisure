package com.example.your100daysofleisure.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.your100daysofleisure.R
import com.example.your100daysofleisure.data.Leisure
import com.example.your100daysofleisure.databinding.ActivityMainBinding
import com.example.your100daysofleisure.utils.SessionManager

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    //lateinit var adapter: LeisureAdapter

    lateinit var your100DaysOfLeisureList: List<Leisure>

    //lateinit var recyclerView: RecyclerView

    lateinit var identifierView: LinearLayout
    lateinit var userNameText: EditText
    lateinit var userZipCodeText: EditText
    lateinit var addButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_main)

        val session = SessionManager(this)

        if (session.getUserName() == null) {
            binding.identifierView.visibility = View.VISIBLE
            //identifierView = findViewById(R.id.identifierView)
            //identifierView.visibility = View.VISIBLE

            binding.userNameText
            binding.userZipCodeText

            session.setUserName(userNameText.toString())
            session.setUserZipCode(userZipCodeText.toString())
            binding.addButton

            addButton.setOnClickListener{
                session.setUserName(userNameText.text.toString())
                session.setUserZipCode(userZipCodeText.text.toString())
                identifierView.visibility = View.GONE
            }
        }

    }
}