package com.example.moviesland.Ui.main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesland.Pojo.MoviesModel.MovieResponse
import com.example.moviesland.Pojo.MoviesModel.MovieResponseResult
import com.example.moviesland.R
import com.example.moviesland.Ui.movie_data.MovieDetailsActivity
import com.example.moviesland.Ui.person_data.PersonDetailsActivity
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import java.lang.String

class MovieSearchAdapter (var mycontext : Context, var result :List<MovieResponseResult> = ArrayList<MovieResponseResult>()):RecyclerView.Adapter<MovieSearchAdapter.MyViewHolder>() {


    fun setMovieList(movieList : List<MovieResponseResult>){
        this.result = movieList as ArrayList<MovieResponseResult>
        notifyDataSetChanged()
    }
    fun addMovielist(movieList: List<MovieResponseResult>) {
        this.result.plus(movieList)

        notifyDataSetChanged()
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val poster_title: TextView = itemView.findViewById(R.id.poster_title)
        val poster_image_view:KenBurnsView= itemView.findViewById(R.id.poster_image_view)
        val generator:RandomTransitionGenerator = RandomTransitionGenerator(12000,DecelerateInterpolator())
        val list_layout: ConstraintLayout = itemView.findViewById(R.id.list_layout)



    }

    override fun getItemCount(): Int {  return result.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_layout_item,parent , false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movieResponseResult: MovieResponseResult = result[position]
        var baseimageURL = "https://image.tmdb.org/t/p/w500"

        holder.poster_title.text = movieResponseResult.title
        Glide.with(mycontext).load(baseimageURL +movieResponseResult.poster_path).into(holder.poster_image_view)
        holder.poster_image_view.setTransitionGenerator(holder.generator)

        val id = movieResponseResult.id
        holder.list_layout.setOnClickListener {

            val intent: Intent = Intent(mycontext, MovieDetailsActivity::class.java)
            intent.putExtra("id", String.valueOf(id))
            mycontext.startActivity(intent)




        }
    }
}