package com.example.bookshelf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.utils.Constants.Companion.BOOK_FRAGMENT
import kotlinx.android.synthetic.main.item_book.view.*
import kotlinx.android.synthetic.main.item_bookmark.view.*

private const val POST_TYPE_BOOK  = 0
private const val POST_TYPE_BOOKMARK  = 1

class  BooksAppAdapter(private val fragment : String, val clickListener: (Book) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class DescriptionViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        fun bind(response : Book, clickListener: (Book) -> Unit){
            Glide.with(itemView)
                .load(response.image)
                .centerCrop()
                .into(itemView.ivBooksItem)
            itemView.tvBooksTitle.text = response.title
            itemView.tvBooksPrice.text = response.price

            itemView.setOnClickListener {
                clickListener(response)
            }

        }
    }
    class BookmarkViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        fun bind(response : Book, clickListener: (Book) -> Unit){
            Glide.with(itemView)
                .load(response.image)
                .centerCrop()
                .into(itemView.ivBookmarkBooksItem)
            itemView.tvBookmarkBooksTitle.text = response.title
            itemView.tvBookmarkBooksPrice.text = response.price
            itemView.rbBookmarkRatingBar.rating = if(response.rating!!.toInt()!=0) (response.rating).toFloat() else 3.toFloat()
            itemView.tvBookmarkBooksSubTitle.text = response.subtitle

            itemView.setOnClickListener {
                clickListener(response)
            }

        }
    }

    private val differCallBack = object :DiffUtil.ItemCallback<Book>(){
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == POST_TYPE_BOOK){
            val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
            return DescriptionViewHolder(layoutInflater)
        }else{
            val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
            return BookmarkViewHolder(layoutInflater)
        }
    }
    override fun getItemCount(): Int {
        val limit = 10
        return Math.min(differ.currentList.size, limit)
    }

    override fun getItemViewType(position: Int): Int {
        return if (fragment == BOOK_FRAGMENT){
            POST_TYPE_BOOK
        }else{
            return POST_TYPE_BOOKMARK
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == POST_TYPE_BOOK){
            (holder as DescriptionViewHolder).bind(differ.currentList[position], clickListener)
        }else{
            (holder as BookmarkViewHolder).bind(differ.currentList[position], clickListener)
        }
    }

/*    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       return when(viewType){
           R.layout.item_book -> HomeViewHolder.DescriptionViewHolder(
               ItemBookitemView.inflate(
                   LayoutInflater.from(parent.context),
                   parent,
                   false
               )
           )
           R.layout.item_bookmark -> HomeViewHolder.BookmarkViewHolder(
               ItemBookmarkitemView.inflate(
                   LayoutInflater.from(parent.context),
                   parent,
                   false
               )
           )
           else -> throw IllegalArgumentException("Invalid ViewType Provided")
       }
    }*/

   /* override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
       when(holder){
           is HomeViewHolder.DescriptionViewHolder -> holder.bind(differ.currentList[position] as Book)
           is HomeViewHolder.BookmarkViewHolder -> holder.bind(differ.currentList[position] as Book)
       }
    }*/


   /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return DescriptionViewHolder(layoutInflater)
    }*/

/*     fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {
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
    }*/



    private var onItemClickListener :((Book) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Book) -> Unit){
        onItemClickListener = listener
    }



}