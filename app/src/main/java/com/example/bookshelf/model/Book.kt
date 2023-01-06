package com.example.bookshelf.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "books"
)
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id :Int? = null,
    val image: String,
    val isbn13: String,
    val price: String,
    val subtitle: String,
    val title: String,
    val url: String,
    val desc : String,
    val authors : String?,
    val publisher : String?,
    val year: String?,
    val rating : String?,
) : Serializable