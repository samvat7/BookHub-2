package com.example.bookhub.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.database.BookDatabase
import com.example.bookhub.database.BookEntity
import com.example.bookhub.databinding.ActivityDescriptionBinding
import com.example.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class DescriptionActivity : AppCompatActivity() {

    //lateinit var binding: ActivityDescriptionBinding

    lateinit var progressBarDesc: ProgressBar

    lateinit var progressLayout: RelativeLayout

    lateinit var txtBookName: TextView

    lateinit var txtBookAuthor: TextView

    lateinit var txtBookPrice: TextView

    lateinit var txtBookRating: TextView

    lateinit var txtBookDesc: TextView

    lateinit var imgBookImageDesc: ImageView

    lateinit var toolbar: Toolbar

    lateinit var btnAddToFav: Button

    var bookId: String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityDescriptionBinding.inflate(layoutInflater)
//
//        setContentView(binding.root)

        setContentView(R.layout.activity_description)

        progressBarDesc = findViewById(R.id.progressBarDesc)

        progressLayout = findViewById(R.id.progressLayout)

        txtBookName = findViewById(R.id.txtBookName)

        txtBookAuthor = findViewById(R.id.txtBookAuthor)

        txtBookPrice = findViewById(R.id.txtBookPrice)

        txtBookRating = findViewById(R.id.txtBookRating)

        txtBookDesc = findViewById(R.id.txtBookDesc)

        imgBookImageDesc = findViewById(R.id.imgBookImageDesc)

        toolbar = findViewById(R.id.toolbar)

        btnAddToFav = findViewById(R.id.btnAddToFav)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Book Description"

        progressLayout.visibility = View.VISIBLE

        progressBarDesc.visibility = View.VISIBLE

        if (ConnectionManager().checkConnectivity(this@DescriptionActivity)) {

            if (intent != null) {

                bookId = intent.getStringExtra("book_id")
            } else {
                finish()
                Toast.makeText(
                    this@DescriptionActivity,
                    "Some Unexpected Error Occurred...",
                    Toast.LENGTH_SHORT
                ).show()
                Log.v("desc activity", "Null Intent")
            }

            if (bookId == "100") {
                finish()
                Toast.makeText(
                    this@DescriptionActivity,
                    "Some Unexpected Error Occurred...",
                    Toast.LENGTH_SHORT
                ).show()
                Log.v("desc activity", "Default Book ID")
            }

            val queue = Volley.newRequestQueue(this@DescriptionActivity)

            val url = "http://13.235.250.119/v1/book/get_book/"

            val jsonParams = JSONObject()

            jsonParams.put("book_id", bookId)

            val jsonRequest =
                object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {

                    Log.v("desc activity", "POST req sent")

                    try {

                        val success = it.getBoolean("success")

                        if (success) {

                            Log.v("desc activity", "success")

                            val bookJsonObject = it.getJSONObject("book_data")

                            progressLayout.visibility = View.GONE

                            val bookImageUrl = bookJsonObject.getString("image")

                            Picasso.get().load(bookJsonObject.getString("image"))
                                .error(R.drawable.default_book_cover).into(imgBookImageDesc)

                            txtBookName.text = bookJsonObject.getString("name")

                            txtBookAuthor.text = bookJsonObject.getString("author")

                            txtBookPrice.text = bookJsonObject.getString("price")

                            txtBookRating.text = bookJsonObject.getString("rating")

                            txtBookDesc.text = bookJsonObject.getString("description")

                            val bookEntity = BookEntity(

                                bookId?.toInt() as Int,
                                txtBookName.text.toString(),
                                txtBookAuthor.text.toString(),
                                txtBookPrice.text.toString(),
                                txtBookRating.text.toString(),
                                txtBookDesc.text.toString(),
                                bookImageUrl
                            )

                            val checkFav = DBAsyncTask(applicationContext, bookEntity, 1).execute()

                            val isFav = checkFav.get()

                            if(isFav){

                                btnAddToFav.text = "Remove from Favourites"

                                val favColor = ContextCompat.getColor(applicationContext, R.color.colorFavourites)

                                btnAddToFav.setBackgroundColor(favColor)
                            }
                            else{

                                btnAddToFav.text = "Add to Favourites"

                                val noFavColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)

                                btnAddToFav.setBackgroundColor(noFavColor)
                            }

                            btnAddToFav.setOnClickListener{

                                if(!DBAsyncTask(applicationContext, bookEntity, 1).execute().get()){

                                    val async = DBAsyncTask(applicationContext, bookEntity, 2).execute()

                                    val result = async.get()

                                    if(result){

                                        Toast.makeText(this@DescriptionActivity, "Book Added to Favourites", Toast.LENGTH_SHORT).show()

                                        btnAddToFav.text = "Remove from Favourites"

                                        val favColor = ContextCompat.getColor(applicationContext, R.color.colorFavourites)

                                        btnAddToFav.setBackgroundColor(favColor)
                                    }
                                    else{

                                        Toast.makeText(this@DescriptionActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else{

                                    val async = DBAsyncTask(applicationContext, bookEntity, 3).execute()

                                    val result = async.get()

                                    if(result){

                                        Toast.makeText(this@DescriptionActivity, "Book Removed from Favourites", Toast.LENGTH_SHORT).show()

                                        btnAddToFav.text = "Add to Favourites"

                                        val noFavColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)

                                        btnAddToFav.setBackgroundColor(noFavColor)
                                    }
                                    else{

                                        Toast.makeText(this@DescriptionActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                        } else {

                            Log.v("desc activity", "Unsuccessful Json Request")

                            Toast.makeText(
                                this@DescriptionActivity,
                                "Some Unexpected Error Occurred...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {


                        Log.v("desc activity", "Catch block executed")

                        Toast.makeText(
                            this@DescriptionActivity,
                            "Some Unexpected Error Occurred...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {


                    Log.v("desc activity", "volley error")
                    Toast.makeText(
                        this@DescriptionActivity,
                        "Volley Error Occurred...",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    override fun getHeaders(): MutableMap<String, String> {

                        Log.v("desc activity", "getHeaders called")

                        val headers = HashMap<String, String>()

                        headers["Content-type"] = "application/json"

                        headers["token"] = "a33b5fbf98f5b2"

                        return headers
                    }
                }

            queue.add(jsonRequest)

        } else {

            val dialog = AlertDialog.Builder(this@DescriptionActivity)

            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->

                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)

                startActivity(settingsIntent)

                finish()
            }
            dialog.setNegativeButton("EXIT") { text, listener ->

                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }

            dialog.create()
            dialog.show()
        }
    }

    class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {


        /*

    Mode 1 = check in DB if book fav or not
    Mode 2 =  Save the book into DB as fav
    Mode 3 = Remove the fav book
     */

        val db = Room.databaseBuilder(context, BookDatabase::class.java, "book-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {


            when(mode){

                1-> {

                    //check DB if the book fav or not
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()

                    return book != null
                }

                2->{

                    //save the book into DB as fav

                    db.bookDao().insertBook(bookEntity)
                    db.close()

                    return true
                }

                3->{

                    //remove the fav book

                    db.bookDao().deleteBook(bookEntity)
                    db.close()

                    return true
                }
            }

            return false
        }


    }
}


