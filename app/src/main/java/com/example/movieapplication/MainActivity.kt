package com.example.movieapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.adapters.MovieAdapter
import com.example.movieapplication.models.Movie
import com.example.movieapplication.ui.AddMovieActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    val thisActivity = this
    var movieList = arrayListOf<Movie>()
    lateinit var adapter : MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        adapter = MovieAdapter(movieList, thisActivity)
        var layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter = adapter

        rv.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                layoutManager.orientation
            )
        )

        btn_plus.setOnClickListener {
            val intent = Intent(this, AddMovieActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide, R.anim.fade_out)
        }


    }

    private fun loadData(){
        var sharedPreferences = getSharedPreferences("movie app", MODE_PRIVATE)

        var gSon = Gson()
        var jSon = sharedPreferences.getString("movie list", "")

        if (jSon == ""){
            movieList = arrayListOf()
        } else{
            val type: Type = object : TypeToken<ArrayList<Movie?>?>() {}.type
            movieList = gSon.fromJson(jSon, type)
        }
    }

    override fun onResume() {
        super.onResume()

        loadData()

        adapter = MovieAdapter(movieList, thisActivity)
        var layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter = adapter
    }
}