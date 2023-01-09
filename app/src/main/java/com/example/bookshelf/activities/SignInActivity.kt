package com.example.bookshelf.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bookshelf.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {

    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_sign_in)

        createFirebaseRequest()

        fabLogInGoogle.setOnClickListener{
            showProgressDialog()
            signInWithGoogle()

        }
    }

    private fun createFirebaseRequest() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, options)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithAccount(account)
            }catch (e: ApiException){
                hideProgressBar()
            }
        }else{
            hideProgressBar()
            return
        }
    }

    private fun firebaseAuthWithAccount(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful){
                   updateUI(auth.currentUser)
                }else{

                    showErrorSnackBar("Invalid E-mail or Password")
                    hideProgressBar()

                }
            }
    }


    private fun updateUI(user: FirebaseUser?) {

        if (user == null){
            hideProgressBar()
            return
        }
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", user.displayName)
        intent.putExtra("email", user.email)
        intent.putExtra("profileUrl", user.photoUrl)
        startActivity(intent)
        finish()

    }
    override fun onStart() {
        super.onStart()

        val user =  auth.currentUser
        if (user!=null){

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    companion object{
        const val SIGN_IN_REQUEST = 1
    }
}