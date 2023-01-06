package com.example.bookshelf.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bookshelf.R
import com.example.bookshelf.activities.MainActivity
import com.example.bookshelf.fragment.BooksFragment.Companion.BOOK_ISBN
import com.example.bookshelf.fragment.BooksFragment.Companion.IMG
import com.example.bookshelf.fragment.BooksFragment.Companion.SUB
import com.example.bookshelf.fragment.BooksFragment.Companion.TITLE
import com.example.bookshelf.viewmodel.BooksViewModel
import kotlinx.android.synthetic.main.fragment_book_description.*
import kotlinx.android.synthetic.main.sv_layout.*


class BookDescriptionFragment : Fragment(R.layout.fragment_book_description) {
    lateinit var viewModel: BooksViewModel
    private var clicked = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel


        val isbn = arguments?.getString(BOOK_ISBN)
        val img = arguments?.getString(IMG)
        val title = arguments?.getString(TITLE)
        val sub = arguments?.getString(SUB)

        tvBookDescriptionTitle.text = title
        tvBookDescriptionSubtitle.text = sub
        Glide.with(requireContext())
            .load(img)
            .into(ivBookDescriptionFragmentImage)

        viewModel.getFullData(isbn!!)
        viewModel.booksAllData.observe(viewLifecycleOwner, Observer {
            Log.d("DIS", it.desc)

            tvBookDescriptionContent.text = it.desc.dropLast(4)
            tvBookDescriptionAuthorNames.text = it.authors
            rbRatingBar.rating = if(it.rating!!.toInt()!=0) (it.rating).toFloat() else 3.toFloat()
            tvBookDescriptionYear.text = "Published Year: ${it.year}"
            tvBookDescriptionPublisher.text = "Publisher: ${it.publisher}"
            btnBuyBook.text = "BUY ${it.price}"

            btnBuyBook.setOnClickListener {view ->
                val bundle = Bundle().apply {
                    putSerializable(WEB_VIEW_KEY, it)
                }
                findNavController().navigate(R.id.action_bookDescriptionFragment_to_bookBuyFragment, bundle)
            }

        })

        btnDescriptionBack.setOnClickListener {
            val action = BookDescriptionFragmentDirections.actionBookDescriptionFragmentToBooksFragment()
            findNavController().navigate(action)
        }


    }


    companion object{
        const val WEB_VIEW_KEY = "WV_KEY"
    }


}