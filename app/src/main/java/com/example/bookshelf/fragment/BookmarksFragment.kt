package com.example.bookshelf.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshelf.R
import com.example.bookshelf.activities.MainActivity
import com.example.bookshelf.adapter.BooksAppAdapter
import com.example.bookshelf.model.Book
import com.example.bookshelf.utils.Constants.Companion.BOOKMARK_FRAGMENT
import com.example.bookshelf.viewmodel.BooksViewModel
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_books.*


class BookmarksFragment : Fragment(R.layout.fragment_bookmark), (Book) -> Unit {
    lateinit var viewModel: BooksViewModel
    lateinit var bookAdapter: BooksAppAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()

        viewModel.getSavedBooks().observe(viewLifecycleOwner, Observer{book ->
            bookAdapter.differ.submitList(book)
        })
    }

    private fun setUpRecyclerView() {
        bookAdapter = BooksAppAdapter(BOOKMARK_FRAGMENT, this)
        rvBookmark.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun invoke(it: Book) {
        Log.d("RV CLICKED", "RV")
        val bundle = Bundle()
        bundle.putString(BooksFragment.BOOK_ISBN, it.isbn13)
        bundle.putString(BooksFragment.IMG, it.image)
        bundle.putString(BooksFragment.TITLE, it.title)
        bundle.putString(BooksFragment.SUB, it.subtitle)

        findNavController().navigate(
            R.id.action_bookmarkFragment_to_bookDescriptionFragment,
            bundle
        )

    }

}