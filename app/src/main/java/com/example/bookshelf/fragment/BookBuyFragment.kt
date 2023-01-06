package com.example.bookshelf.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.bookshelf.R
import com.example.bookshelf.activities.MainActivity
import com.example.bookshelf.viewmodel.BooksViewModel
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_buy_book.*

class BookBuyFragment:Fragment(R.layout.fragment_buy_book) {
    lateinit var viewModel: BooksViewModel
    private val args: BookBuyFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

     //   webViewBuyBook!!.webViewClient = WebViewClient()


        viewModel.booksAllData.observe(viewLifecycleOwner, Observer{
           // val book =args.buyBook
            webViewBuyBook.apply {
                val url = "https://itbook.store/go/buy/${it.isbn13}"
                webViewClient = WebViewClient()

                webViewBuyBook!!.webViewClient = object : WebViewClient(){
                    override fun onPageFinished(view: WebView?, url: String?) {
                        Log.d("WebView", "onPageFinished " + url)
                    }
                }
                webViewBuyBook.webChromeClient = object  : WebChromeClient(){
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        showProgressBar()
                        if (newProgress > 60){
                            hideProgressBar()
                        }
                    }
                }
                loadUrl(url)
            }
        })

    }

    private fun hideProgressBar() {
        webViewProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        webViewProgressBar.visibility = View.VISIBLE
    }
}