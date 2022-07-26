package com.example.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bookhub.R
import com.example.bookhub.adapter.FavouriteRecyclerAdapter
import com.example.bookhub.database.BookDatabase
import com.example.bookhub.database.BookEntity
import com.example.bookhub.databinding.ActivityDescriptionBinding.inflate
import com.example.bookhub.databinding.RecylerFavouritesSingleRowBinding.inflate


class FavouritesFragment : Fragment() {

    lateinit var recyclerFavourite: RecyclerView

    lateinit var progressLayoutFav : RelativeLayout

    lateinit var progressBarFav: ProgressBar

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: FavouriteRecyclerAdapter

    var dbBookList = listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerFavourite = view.findViewById(R.id.recyclerFavourites)

        progressLayoutFav = view.findViewById(R.id.progressLayoutFav)

        progressBarFav = view.findViewById(R.id.progressBarFav)

        layoutManager = GridLayoutManager(activity as Context, 2)

        dbBookList = RetrieveFavourites(activity as Context).execute().get()

        if(activity != null){

            progressLayoutFav.visibility = View.GONE

            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, dbBookList)

            recyclerFavourite.adapter = recyclerAdapter

            recyclerFavourite.layoutManager = layoutManager
        }

        return view
    }

    class RetrieveFavourites(val context: Context): AsyncTask<Void, Void, List<BookEntity>>(){

        override fun doInBackground(vararg p0: Void?): List<BookEntity> {

            val db = Room.databaseBuilder(context, BookDatabase::class.java, "book-db").build()

            return db.bookDao().getAllBooks()
        }

    }
}