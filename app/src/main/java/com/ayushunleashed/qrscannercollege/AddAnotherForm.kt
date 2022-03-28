package com.ayushunleashed.qrscannercollege

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddAnotherForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_another_form)
    }

    fun QuitApp(view: View) {
        finishAffinity()
    }
    fun GoBackToFromActivity(view: View) {
        val intent = Intent(this,FormActivity::class.java)
        startActivity(intent)
    }
}