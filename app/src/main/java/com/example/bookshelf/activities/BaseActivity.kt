package com.example.bookshelf.activities

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bookshelf.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.progress_dialog_with_text.*

open class BaseActivity: AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var mProgressDialog: Dialog
    private lateinit var mTextProgressBar : Dialog


    fun showProgressDialog(){
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.progress_dialog)

        /// To make bg transparent
        mProgressDialog.window!!.setBackgroundDrawable(getDrawable(android.R.color.transparent))

        mProgressDialog.setCancelable(false)
        mProgressDialog.show()

    }

    fun hideProgressBar(){
        mProgressDialog.dismiss()
    }

    fun showTextProgressBar(text: String ="Please Wait"){
        mTextProgressBar = Dialog(this)
        mTextProgressBar.setContentView(R.layout.progress_dialog_with_text)
        mTextProgressBar.window!!.setBackgroundDrawable(getDrawable(android.R.color.transparent))

        mTextProgressBar.textViewProgressBar.text = text

        mTextProgressBar.setCancelable(false)
        mTextProgressBar.show()


    }

    fun hideTextProgressBar(){
        mTextProgressBar.dismiss()
    }

    fun doubleBackToExit(){
        if (doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this,"Please click again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({doubleBackToExitPressedOnce=false}, 1100)
    }
    fun showErrorSnackBar(message:String){
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view


        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.myRed))
        snackBar.show()
    }

    fun toast(context: Context, text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun isEmailValid(email : CharSequence):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}