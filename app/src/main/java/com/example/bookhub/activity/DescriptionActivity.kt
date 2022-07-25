package com.example.bookhub.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.databinding.ActivityDescriptionBinding
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

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Book Description"

        progressLayout.visibility = View.VISIBLE

        progressBarDesc.visibility = View.VISIBLE

        if(intent!=null){

            bookId = intent.getStringExtra("book_id")
        }
        else{
            finish()
            Toast.makeText(this@DescriptionActivity, "Some Unexpected Error Occurred...", Toast.LENGTH_SHORT).show()
            Log.v("desc activity", "Null Intent")
        }

        if(bookId == "100"){
            finish()
            Toast.makeText(this@DescriptionActivity, "Some Unexpected Error Occurred...", Toast.LENGTH_SHORT).show()
            Log.v("desc activity", "Default Book ID")
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)

        val url = "http://13.235.250.119/v1/book/get_book/"

        val jsonParams = JSONObject()

        jsonParams.put("book_id", bookId)

        val jsonRequest = object: JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {

            Log.v("desc activity", "POST req sent")

            try{

                val success = it.getBoolean("success")

                if(success){

                    Log.v("desc activity", "success")

                    val bookJsonObject = it.getJSONObject("book_data")

                    progressLayout.visibility = View.GONE

                    Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.default_book_cover).into(imgBookImageDesc)

                    txtBookName.text = bookJsonObject.getString("name")

                    txtBookAuthor.text = bookJsonObject.getString("author")

                    txtBookPrice.text = bookJsonObject.getString("price")

                    txtBookRating.text = bookJsonObject.getString("rating")

                    txtBookDesc.text = bookJsonObject.getString("description")
                }
                else{

                    Log.v("desc activity", "Unsuccessful Json Request")

                    Toast.makeText(this@DescriptionActivity, "Some Unexpected Error Occurred...", Toast.LENGTH_SHORT).show()
                }
            }
            catch(e: Exception){


                    Log.v("desc activity", "Catch block executed")

                    Toast.makeText(this@DescriptionActivity, "Some Unexpected Error Occurred...", Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener {


            Log.v("desc activity", "volley error")
            Toast.makeText(this@DescriptionActivity, "Volley Error Occurred...", Toast.LENGTH_SHORT).show()
        }){
            override fun getHeaders(): MutableMap<String, String> {

                Log.v("desc activity", "getHeaders called")

                val headers = HashMap<String,String>()

                headers["Content-type"] = "application/json"

                headers["token"] = "a33b5fbf98f5b2"

                return headers
            }
        }

        queue.add(jsonRequest)
    }
}


