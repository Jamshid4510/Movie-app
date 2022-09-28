package com.example.movieapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movieapplication.R
import com.example.movieapplication.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type

class AddMovieActivity : AppCompatActivity() {
    var movieList = arrayListOf<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        btn_save.setOnClickListener {
            var correct = true
            var name = et_name.text.toString().trim()
            var authors = et_authors.text.toString().trim()
            var aboutMovie = et_about_movie.text.toString().trim()
            var data = et_data.text.toString().trim()

            if (name.isEmpty()){
                et_name.setError("Enter movie name")
                correct = false
            }
            if (authors.isEmpty()){
                et_authors.setError("Enter authors")
                correct = false
            }
            if (aboutMovie.isEmpty()){
                et_about_movie.setError("Enter some info")
                correct = false
            }
            if (data.isEmpty()){
                et_data.setError("Enter data")
                correct = false
            }

            if (correct){
                var myMovie = Movie(name, authors, aboutMovie, data)

                loadData()
                movieList.add(myMovie)
                saveData()
            }
        }

    }
    private fun saveData(){
        var sharedPreferences = getSharedPreferences("movie app", MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        var gSon = Gson()
        var jSon = gSon.toJson(movieList)

        editor.putString("movie list", jSon)
        editor.apply()

//        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()

        et_name.setText("")
        et_authors.setText("")
        et_about_movie.setText("")
        et_data.setText("")
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

}