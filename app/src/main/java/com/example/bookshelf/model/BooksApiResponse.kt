package com.example.bookshelf.model

import com.example.bookshelf.model.Book

data class BooksApiResponse(
    val books: List<Book>,
    val error: String,
    val total: String
)