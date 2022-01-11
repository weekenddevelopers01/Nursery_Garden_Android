package com.example.nurserygardenandroid.ui.activity

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.nurserygardenandroid.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: Dialog


    public fun showSnackBar(msg:String, isError:Boolean){
        var snackbar:Snackbar = Snackbar.make(findViewById(R.id.container), msg, Snackbar.LENGTH_SHORT)
        var snackbarView: View = snackbar.view

        if(isError){
            snackbarView.setBackgroundColor(Color.RED)
        }else{
//            snackbarView.setBackgroundColor(Color.GREEN)
        }
        snackbar.show()
    }

    public fun showProgressBar(str:String){
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        var textView:TextView = progressDialog.findViewById(R.id.progress_txt)
        textView.text =str

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    public fun dismissProgressBar(){
        progressDialog.dismiss()
    }
}