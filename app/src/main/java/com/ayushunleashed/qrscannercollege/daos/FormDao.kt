package com.ayushunleashed.qrscannercollege.daos

import com.ayushunleashed.qrscannercollege.models.FormModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FormDao {
    private val db = FirebaseFirestore.getInstance()
    private val formsCollection = db.collection("forms")

    fun addForms(form: FormModel?)
    {
        GlobalScope.launch (Dispatchers.IO){
            //if user is not null
            form?.let{
                formsCollection.add(form)
            }
        }
    }
}