package com.example.bookshelf.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bookshelf.R
import com.example.bookshelf.database.BookDatabase
import com.example.bookshelf.repo.BooksRepository
import com.example.bookshelf.viewmodel.BooksViewModel
import com.example.bookshelf.viewmodel.BooksViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var auth: FirebaseAuth
    lateinit var viewModel: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        auth = FirebaseAuth.getInstance()

        val booksRepository = BooksRepository(BookDatabase(this))
        val viewModelFactory = BooksViewModelFactory(booksRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BooksViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController =navHostFragment.navController
        bottomNavView.setupWithNavController(navController)

       /* button.setOnClickListener{
            AuthUI.getInstance().signOut(this).addOnCompleteListener{
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }.addOnFailureListener{
                toast(this, "Failed in signing out")
            }

        }*/

    }
}