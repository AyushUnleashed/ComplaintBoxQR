package com.ayushunleashed.qrscannercollege

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun GoToCamera(view: View) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}