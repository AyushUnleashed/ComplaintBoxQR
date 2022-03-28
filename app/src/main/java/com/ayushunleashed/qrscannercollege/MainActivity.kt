package com.ayushunleashed.qrscannercollege

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


private const val CAMERA_RQ = 101

class MainActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkForPermissions(android.Manifest.permission.CAMERA,"camera",CAMERA_RQ)
        codeScanner()
    }
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


    private fun codeScanner()
    {
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS// or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                    Log.d("CHECK",it.text)
                    //Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()

                    val intent = Intent(this,FormActivity::class.java)
                    startActivity(intent);

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Snackbar.make(scanner_view,"Camera initialization error: ${it.message}",Snackbar.LENGTH_SHORT).show()
                //Toast.makeText(this, "Camera initialization error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //Permission Checker || if permission not granted show dialog or request permission
    private fun checkForPermissions(permission: String,name:String,requestCode:Int)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            when
            { // it is like switch

                /*1*/ ContextCompat.checkSelfPermission(applicationContext,permission) == PackageManager.PERMISSION_GRANTED ->
                {
                    //Toast.makeText(applicationContext,"$name permission granted",Toast.LENGTH_SHORT).show()
                }
                /*2*/ shouldShowRequestPermissionRationale(permission) -> showDialog(permission,name,requestCode)

                /*3*/ else -> ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)


            }
        }

    }
    fun goToPermissionSettings() //redirects to app settings
    {

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    // Checker function default present
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        fun innerCheck(name: String){
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(applicationContext,"$name Permission Refused\nGrant Permission",Toast.LENGTH_LONG).show()
                goToPermissionSettings()
            }else {
                Toast.makeText(applicationContext, "$name Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }

        when(requestCode){
            CAMERA_RQ -> innerCheck("camera")
        }

    }

    // Makes dialog
    private fun showDialog(permission: String,name:String,requestCode:Int)
    {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required in this app, if denied change in settings")
            setTitle("Permission required")
            setPositiveButton("Ok"){dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),requestCode)
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

}