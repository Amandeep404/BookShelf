package com.example.bookshelf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import kotlinx.android.synthetic.main.item_book.view.*

class  BooksAppAdapter : RecyclerView.Adapter<BooksAppAdapter.DescriptionViewHolder>() {
    inner class DescriptionViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val differCallBack = object :DiffUtil.ItemCallback<Book>(){
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
           return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return DescriptionViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {
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
}