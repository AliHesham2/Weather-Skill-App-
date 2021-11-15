package com.example.weather_app.view.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.weather_app.R
import com.google.android.material.snackbar.Snackbar


class PopUpMsg {
    companion object{
        private lateinit var  mProgressDialog : Dialog

        fun alertMsg(view: View, msg: String){
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).also { snackBar ->
                snackBar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.red))
            }.show()
        }

        fun toastMsg(context: Context, msg:String){
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
        }

        fun showDialogue(context: Context){
            mProgressDialog = Dialog(context)
            mProgressDialog.setContentView(R.layout.dialog_custom_progress)
            mProgressDialog.setCancelable(false)
            mProgressDialog.show()
        }
        fun hideDialogue(){
            mProgressDialog.dismiss()
        }



    }
}