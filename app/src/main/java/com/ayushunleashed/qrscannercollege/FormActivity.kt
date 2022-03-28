package com.ayushunleashed.qrscannercollege

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.RadioButton
import android.widget.Toast
import com.ayushunleashed.qrscannercollege.daos.FormDao
import com.ayushunleashed.qrscannercollege.models.FormModel
import com.budiyev.android.codescanner.CodeScanner
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private val formsCollection = db.collection("forms")
    private lateinit var complaintType:String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        rbGroup.setOnCheckedChangeListener { radioGroup, i ->
            var rb = findViewById<RadioButton>(i)

            if(rb!=null)
            {
                complaintType = rb.text.toString();
                //Toast.makeText(this, rb.text.toString(),Toast.LENGTH_SHORT).show()
            }
            else
            {
               complaintType = "Other"
            }
        }

        btnAddForm.setOnClickListener{
            addForms()
        }
    }

    fun addForms() {

        //val title = etvEnterTitle.text.toString()
        val description = etvEnterDescription.text.toString()
        val form = FormModel(complaintType, description)
        if (complaintType.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "All Fields are required", Toast.LENGTH_SHORT).show();
        } else {
            formsCollection.add(form).addOnSuccessListener {
                Toast.makeText(this, "Feedback Added", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,AddAnotherForm::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to send feedback Form", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,AddAnotherForm::class.java)
                startActivity(intent)
            }
        }
    }
}