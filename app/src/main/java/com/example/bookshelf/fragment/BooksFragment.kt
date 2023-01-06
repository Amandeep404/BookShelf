package com.example.bookshelf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshelf.R
import com.example.bookshelf.activities.MainActivity
import com.example.bookshelf.adapter.BooksAppAdapter
import com.example.bookshelf.model.Book
import com.example.bookshelf.utils.Resource
import com.example.bookshelf.viewmodel.BooksViewModel
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BooksFragment : Fragment(R.layout.fragment_books) {
    lateinit var viewModel: BooksViewModel
    lateinit var booksdapter: BooksAppAdapter

    val TAG = "NEW_BOOKS_FRAGMENT"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setUpRecycleView()


        viewModel.newBooks.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                    it.data?.let {
                        booksdapter.differ.submitList(it.books)
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

        viewModel.searchBooks("data")
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
                        Log.e(TAG, "An error occured: $it")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })



        booksdapter.setOnItemClickListener {
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

    private fun setUpRecycleView() {
        booksdapter = BooksAppAdapter()
        rvRecommended.apply {
            adapter = booksdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
  

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }
    private fun showProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    companion object{
        const val BOOK_ISBN = "ISBN"
        const val SUB = "SUB"
        const val TITLE = "TITLE"
        const val IMG = "IMG"
    }

}