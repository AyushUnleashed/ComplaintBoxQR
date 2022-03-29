package com.ayushunleashed.qrscannercollege

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.ayushunleashed.qrscannercollege.daos.FormDao
import com.ayushunleashed.qrscannercollege.models.FormModel
import com.budiyev.android.codescanner.CodeScanner
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_form.*
import org.json.JSONObject.NULL

class FormActivity : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()
    private val formsCollection = db.collection("forms")
    private lateinit var complaintType:String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        complaintType = "Other"
        rbGroup.check(rbOther.id)
        rbGroup.setOnCheckedChangeListener { radioGroup, i ->
            var rb = findViewById<RadioButton>(i)

            if(rb!=null)
            {
                complaintType = rb.text.toString();
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
       //Toast.makeText(this,"Yogi add form is called",Toast.LENGTH_SHORT).show()

        //val title = etvEnterTitle.text.toString()
        var description = etvEnterDescription.text.toString()
        if(description==NULL)
        {
            description=""
        }
        val form = FormModel(QRScanOutput,complaintType,description)


            formsCollection.add(form).addOnSuccessListener {
                Toast.makeText(this, "Feedback Added", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, AddAnotherForm::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to send feedback Form", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, AddAnotherForm::class.java)
                startActivity(intent)
            }

    }
}