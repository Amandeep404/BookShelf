package com.example.bookshelf.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.bookshelf.databinding.ItemBookBinding
import com.example.bookshelf.databinding.ItemBookmarkBinding
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BooksApiResponse
import kotlinx.android.synthetic.main.item_book.view.*
import retrofit2.Response

sealed class HomeViewHolder(binding: ViewBinding):RecyclerView.ViewHolder(binding.root) {


}