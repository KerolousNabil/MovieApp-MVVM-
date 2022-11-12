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
import com.example.moviesland.Pojo.MoviesModel.MovieCreditsCrew
import com.example.moviesland.Pojo.MoviesModel.MovieDetailsProductionCompanies
import com.example.moviesland.Pojo.PersonModel.PersonImagesProfiles
import com.example.moviesland.Pojo.PersonModel.PersonResponseResult
import com.example.moviesland.R
import com.example.moviesland.Ui.person_data.PersonDetailsActivity
import com.example.moviesland.Ui.person_data.PersonProfilesImageAdapter
import com.flaviofaria.kenburnsview.KenBurnsView
import java.lang.String

class MovieCreditsProductionCompaniesAdapter(var mycontext : Context, val result :List<MovieDetailsProductionCompanies>): RecyclerView.Adapter<MovieCreditsProductionCompaniesAdapter.MyViewHolder>()  {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val productionCompanyName: TextView = itemView.findViewById(R.id.productionCompanies_name)
        val productionCompanyImage: ImageView = itemView.findViewById(R.id.productionCompanies_image_view)
        val list_layout: CardView = itemView.findViewById(R.id.list_layout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.production_companies_layout,parent , false)
        return MovieCreditsProductionCompaniesAdapter.MyViewHolder(itemView)    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var baseimageURL = "https://image.tmdb.org/t/p/w500"

        val movieDetailsProductionCompanies: MovieDetailsProductionCompanies = result[position]
        Glide.with(mycontext).load(baseimageURL+movieDetailsProductionCompanies.logo_path).into(holder.productionCompanyImage)
        holder.productionCompanyName.text = movieDetailsProductionCompanies.name



    }

    override fun getItemCount(): Int { return  result.size    }
}