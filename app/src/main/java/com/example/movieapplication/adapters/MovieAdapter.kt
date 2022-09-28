package com.example.movieapplication.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.R
import com.example.movieapplication.models.Movie
import com.example.movieapplication.ui.EditMovieActivity
import com.example.movieapplication.ui.InfoMovieActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(var movieList: ArrayList<Movie>, var mActivity: Activity) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>(){
    inner class MyViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(movie: Movie, position: Int){
            itemView.tv_title.text = movie.name
            itemView.tv_authors.text = movie.authors
            itemView.tv_data.text = movie.data

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, InfoMovieActivity::class.java)
                intent.putExtra("movie", movie)
                itemView.context.startActivity(intent)
                mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            itemView.btn_edit.setOnClickListener {
                val intent = Intent(itemView.context, EditMovieActivity::class.java)
                intent.putExtra("movie", movie)
                intent.putExtra("position", position)
                itemView.context.startActivity(intent)
            }
            
            itemView.btn_delete.setOnClickListener {
                movieList.removeAt(position)

                notifyItemRemoved(position)
                notifyItemRangeChanged(position, movieList.size)

                saveData(itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        var myViewHolder = MyViewHolder(itemView)

        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(movieList[position], position)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun saveData(itemView: View){
        var sharedPreferences = itemView.context.getSharedPreferences("movie app", AppCompatActivity.MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        var gSon = Gson()
        var jSon = gSon.toJson(movieList)

        editor.putString("movie list", jSon)
        editor.apply()
    }
}