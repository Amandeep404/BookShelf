package com.example.bookshelf.api

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BooksApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApiService {
    @GET("new")
    suspend fun getNewBooks() : Response<BooksApiResponse>

    @GET("search/{q}/{page}")
    suspend fun searchBooks(
        @Path("q")
        searchQuery : String,
        @Path("page")
        pageNumber : Int = 1
    ): Response<BooksApiResponse>

    @GET("books/{isbn13}")
    suspend fun isbnCall(
        @Path("isbn13")
        isbn13 : String
    ): Book
}