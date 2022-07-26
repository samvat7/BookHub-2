package com.example.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R
import com.example.bookhub.database.BookEntity
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class FavouriteRecyclerAdapter(val context: Context, val bookList: List<BookEntity>): RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouritesViewHolder>() {

    class FavouritesViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtBookName: TextView = view.findViewById(R.id.txtFavBookTitle)

        val txtBookAuthor: TextView = view.findViewById(R.id.txtFavBookAuthor)

        val txtBookPrice: TextView = view.findViewById(R.id.txtFavBookPrice)

        val txtBookRating: TextView = view.findViewById(R.id.txtFavBookRating)

        val imgBookImage: ImageView = view.findViewById(R.id.imgFavBookImage)

        val llContent: LinearLayout = view.findViewById(R.id.llFavContent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyler_favourites_single_row, parent, false)

        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {

        val book = bookList[position]

        holder.txtBookName.text = book.bookName

        holder.txtBookAuthor.text = book.bookAuthor

        holder.txtBookPrice.text = book.bookPrice

        holder.txtBookRating.text = book.bookRating

        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage)
    }

    override fun getItemCount(): Int {

        return bookList.size
    }
}