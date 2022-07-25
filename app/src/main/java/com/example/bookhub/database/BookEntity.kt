package com.example.bookhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")

data class BookEntity {

            @PrimaryKey val book_id: Int,
            @ColumnInfo(name = "") val book_id: Int,
            val bookName: String,
            val bookAuthor: String,
            val bookPrice: String,
            val bookRating: String,
            val bookDesc: String,
            val bookImage: String
}