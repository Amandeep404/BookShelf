package com.example.bookshelf.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookshelf.model.Book

@Dao
interface databaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(book: Book):Long // this function deals with updating and insertion of data

    @Query("SELECT * FROM books")
    fun getAllBooks() :LiveData<List<Book>>

    @Delete
    suspend fun deleteBook(book: Book)

}