package com.example.moviesland.Ui.movie_data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesland.Pojo.MoviesModel.MovieCreditsCast
import com.example.moviesland.Pojo.MoviesModel.MovieCreditsCrew
import com.example.moviesland.Pojo.PersonModel.PersonImagesProfiles
import com.example.moviesland.Pojo.PersonModel.PersonResponseResult
import com.example.moviesland.R
import com.example.moviesland.Ui.person_data.PersonDetailsActivity
import com.example.moviesland.Ui.person_data.PersonProfilesImageAdapter
import com.flaviofaria.kenburnsview.KenBurnsView
import java.lang.String

class MovieCreditsCastAdapter(var mycontext : Context, val result :List<MovieCreditsCast>): RecyclerView.Adapter<MovieCreditsCastAdapter.MyViewHolder>()  {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val movieCreditName: TextView = itemView.findViewById(R.id.movie_credits_name)
        val movieCreditCharacter: TextView = itemView.findViewById(R.id.movie_credits_Character)
        val movieCreditImageView: ImageView = itemView.findViewById(R.id.movie_credits_image_view)
        val list_layout: CardView = itemView.findViewById(R.id.list_layout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_credits_layout,parent , false)
        return MovieCreditsCastAdapter.MyViewHolder(itemView)    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var baseimageURL = "https://image.tmdb.org/t/p/w500"

        val movieCreditCast: MovieCreditsCast = result[position]
        Glide.with(mycontext).load(baseimageURL+movieCreditCast.profile_path).into(holder.movieCreditImageView)
        holder.movieCreditName.text = movieCreditCast.name
        holder.movieCreditCharacter.text = "Character : " + movieCreditCast.character

        holder.list_layout.setOnClickListener {

            val intent: Intent = Intent(mycontext, PersonDetailsActivity::class.java)
            intent.putExtra("id", String.valueOf(movieCreditCast.id))
            mycontext.startActivity(intent)


        }

    }

    override fun getItemCount(): Int { return  result.size    }
}