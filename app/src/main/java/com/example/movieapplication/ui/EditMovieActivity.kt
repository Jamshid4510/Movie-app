package com.example.movieapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movieapplication.R
import com.example.movieapplication.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.activity_edit_movie.*
import java.lang.reflect.Type

class EditMovieActivity : AppCompatActivity() {
    var movieList = arrayListOf<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)

        var myMovie = intent.getSerializableExtra("movie") as Movie
        var position = intent.getIntExtra("position", 0)

        et_edit_name.setText(myMovie.name)
        et_edit_authors.setText(myMovie.authors)
        et_edit_about_movie.setText(myMovie.about_movie)
        et_edit_data.setText(myMovie.data)

        btn_edit_save.setOnClickListener {
            var correct = true
            var name = et_edit_name.text.toString().trim()
            var authors = et_edit_authors.text.toString().trim()
            var aboutMovie = et_edit_about_movie.text.toString().trim()
            var data = et_edit_data.text.toString().trim()

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
//                movieList.add(myMovie)
                movieList[position] = myMovie
                saveData()
            }
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

    private fun saveData(){
        var sharedPreferences = getSharedPreferences("movie app", MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        var gSon = Gson()
        var jSon = gSon.toJson(movieList)

        editor.putString("movie list", jSon)
        editor.apply()

//        Toast.makeText(this, "Edited!", Toast.LENGTH_SHORT).show()

        et_edit_name.setText("") // 71 231 60-00
        et_edit_authors.setText("")
        et_edit_about_movie.setText("")
        et_edit_data.setText("")

        finish()

//        overridePendingTransition(R.anim.slide, R.anim.fade_out)
    }

}