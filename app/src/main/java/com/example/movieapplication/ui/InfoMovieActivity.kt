package com.example.movieapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapplication.R
import com.example.movieapplication.models.Movie
import kotlinx.android.synthetic.main.activity_info_movie.*

class InfoMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_movie)

        var myMovie = intent.getSerializableExtra("movie") as Movie

        tv_info_name.text = "Movie name: ${myMovie.name}"
        tv_info_authors.text = "Movie authors: ${myMovie.authors}"
        tv_info_about_movie.text = "About movie: ${myMovie.about_movie}"
        tv_info_data.text = "Data: ${myMovie.data}"

        btn_info_close.setOnClickListener {
            finish()
//            overridePendingTransition(R.anim.slide, R.anim.fade_out)
        }

    }
}