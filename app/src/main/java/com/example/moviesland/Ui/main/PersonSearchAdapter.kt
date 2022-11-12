package com.example.moviesland.Ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesland.Pojo.MoviesModel.MovieResponseResult
import com.example.moviesland.Pojo.PersonModel.PersonResponseResult
import com.example.moviesland.R
import com.example.moviesland.Ui.person_data.PersonDetailsActivity
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import java.lang.String

class PersonSearchAdapter  (var mycontext : Context, var result :List<PersonResponseResult> = ArrayList<PersonResponseResult>()):RecyclerView.Adapter<PersonSearchAdapter.MyViewHolder>() {

    fun setPersonList(personList : List<PersonResponseResult>){
        this.result = personList as ArrayList<PersonResponseResult>
        notifyDataSetChanged()
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val person_name: TextView = itemView.findViewById(R.id.poster_title)
        val person_profile: KenBurnsView = itemView.findViewById(R.id.poster_image_view)
        val generator: RandomTransitionGenerator = RandomTransitionGenerator(12000, DecelerateInterpolator())
        val list_layout: ConstraintLayout = itemView.findViewById(R.id.list_layout)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_layout_item,parent , false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val personResponseResult: PersonResponseResult = result[position]
        var baseimageURL = "https://image.tmdb.org/t/p/w500"

        holder.person_name.text = personResponseResult.name
        Glide.with(mycontext).load(baseimageURL +personResponseResult.profile_path).into(holder.person_profile)
        holder.person_profile.setTransitionGenerator(holder.generator)
        val id = personResponseResult.id
        holder.list_layout.setOnClickListener {

            val intent:Intent = Intent(mycontext, PersonDetailsActivity::class.java)
            intent.putExtra("id", String.valueOf(id))
            mycontext.startActivity(intent)




        }

    }

    override fun getItemCount(): Int {
        return result.size
    }
}