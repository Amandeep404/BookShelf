package com.example.bookshelf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.bookshelf.R
import com.example.bookshelf.activities.MainActivity
import com.example.bookshelf.adapter.BooksAppAdapter
import com.example.bookshelf.model.Book
import com.example.bookshelf.utils.Constants.Companion.BOOK_FRAGMENT
import com.example.bookshelf.utils.Resource
import com.example.bookshelf.viewmodel.BooksViewModel
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BooksFragment : Fragment(R.layout.fragment_books), (Book) -> Unit {
    lateinit var viewModel: BooksViewModel
    lateinit var booksdapter: BooksAppAdapter

    val TAG = "NEW_BOOKS_FRAGMENT"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        booksdapter = BooksAppAdapter(BOOK_FRAGMENT, this)
        rvRecommended.apply {
            adapter = booksdapter
            layoutManager = GridLayoutManager(activity, 2)
        }

        viewModel.newBooks.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                    it.data?.let {response ->
                        booksdapter.differ.submitList(response.books)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    it.message?.let {
                        Log.e(TAG, "An error occurred: $it")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })

        svBooks.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query!!.isNotEmpty()){
                    llTvRecommend.visibility = View.GONE
                    viewModel.searchBooks(query)
                    searchBookFunction()
                    Log.d("ifRecyclerView", "RV")

                }else{

                    callNewBookRequest()
                    Log.d("elseRecyclerView", "RV")
                    svBooks.clearFocus()
                    viewModel.getNewBooks()
                }
                Log.d("onQuerySubmit", "RV")

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val search = newText!!.toString()
               if (search.isNotEmpty()){
                   llTvRecommend.visibility = View.GONE
                   MainScope().launch {
                       delay(100L)
                       viewModel.searchBooks(search)
                       searchBookFunction()
                   }
               }else{
                   svBooks.clearFocus()
                   viewModel.getNewBooks()
                   callNewBookRequest()
               }
                return false
            }

        })

        svBooks.setOnCloseListener {
            svBooks.clearFocus()
            viewModel.getNewBooks()
            callNewBookRequest()
            false
        }


    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    companion object{
        const val BOOK_ISBN = "ISBN"
        const val SUB = "SUB"
        const val TITLE = "TITLE"
        const val IMG = "IMG"
    }


    private fun callNewBookRequest(){
        Log.d("NewBooks() Called" , "RV")
        viewModel.newBooks.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                    it.data?.let {response ->
                        booksdapter.differ.submitList(response.books)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    it.message?.let {
                        Log.e(TAG, "An error occured: $it")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }

    private fun searchBookFunction(){
        Log.d("SearchBooks() Called" , "RV")
        viewModel.searchBooks.observe(viewLifecycleOwner, Observer{
            println(it.data)
            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                    it.data?.let {response ->
                        booksdapter.differ.submitList(response.books)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    it.message?.let {
                        Log.e(TAG, "An error occurred: $it")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }

    override fun invoke(it: Book) {
        Log.d("RV CLICKED", "RV")
        val bundle = Bundle()
        bundle.putString(BOOK_ISBN , it.isbn13)
        bundle.putString(IMG , it.image)
        bundle.putString(TITLE , it.title)
        bundle.putString(SUB , it.subtitle)

        findNavController().navigate(
            R.id.action_booksFragment_to_bookDescriptionFragment,
            bundle
        )

    }




}