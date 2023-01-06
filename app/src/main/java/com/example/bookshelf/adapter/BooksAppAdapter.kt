package com.example.bookshelf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import kotlinx.android.synthetic.main.item_book.view.*

class  BooksAppAdapter : RecyclerView.Adapter<HomeViewHolder>() {

    private val ITEM_TYPE_DESCRIPTION = 0
    private val ITEM_TYPE_DATA_SCIENCE = 0

    private val differCallBack = object :DiffUtil.ItemCallback<Book>(){
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
           return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        if (viewType == ITEM_TYPE_DESCRIPTION){
            return HomeViewHolder.DescriptionViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
            )

        }else
            return HomeViewHolder.DataScienceViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
            )
    }


    override fun onBindViewHolder(holder: HomeViewHolder.DescriptionViewHolder, position: Int) {
        val description = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(context)
                .load(description.image)
                .centerCrop()
                .into(ivBooksItem)

            tvBooksTitle.text = description.title
            tvBooksPrice.text = description.price

            setOnClickListener{
                onItemClickListener?.let {
                    it(description)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        val limit = 8
        return Math.min(differ.currentList.size, limit)
    }

    private var onItemClickListener :((Book) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Book) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}