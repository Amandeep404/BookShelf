package com.example.bookshelf.repo

import com.example.bookshelf.api.RetrofitInstance
import com.example.bookshelf.database.BookDatabase
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BooksApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Query

class BooksRepository(
    val db : BookDatabase
) {
    suspend fun getNewBooks() = RetrofitInstance.booksApi.getNewBooks()

    suspend fun searchNews(searchQuery: String,pageNumber :Int ) =
        RetrofitInstance.booksApi.searchBooks(searchQuery, pageNumber)

    suspend fun upsert(book : Book) = db.getBooksDao().upsert(book)
    fun getSavedBook() =db.getBooksDao().getAllBooks()
    suspend fun deleteBook(book: Book) = db.getBooksDao().deleteBook(book)

    suspend fun getDetail(isbn: String) = RetrofitInstance.booksApi.isbnCall(isbn)
}