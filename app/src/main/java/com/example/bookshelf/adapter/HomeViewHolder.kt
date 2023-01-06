package com.example.bookshelf.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BooksApiResponse
import kotlinx.android.synthetic.main.item_book.view.*
import retrofit2.Response

sealed class HomeViewHolder(view:View):RecyclerView.ViewHolder(view) {

    class DescriptionViewHolder(view: View) : HomeViewHolder(view){
        fun bind(response : Book){
            Glide.with(itemView)
                .load(response.image)
                .centerCrop()
                .into(itemView.ivBooksItem)

            itemView.tvBooksTitle.text = response.title
            itemView.tvBooksPrice.text = response.price
        }
    }
    class DataScienceViewHolder(view: View) : HomeViewHolder(view){
        fun bind(response : Book){
            Glide.with(itemView)
                .load(response.image)
                .centerCrop()
                .into(itemView.ivBooksItem)

            itemView.tvBooksTitle.text = response.title
            itemView.tvBooksPrice.text = response.price
        }
    }
}