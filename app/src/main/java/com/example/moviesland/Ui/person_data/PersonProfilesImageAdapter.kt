package com.example.moviesland.Ui.person_data

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesland.Pojo.PersonModel.PersonImagesProfiles
import com.example.moviesland.R
import com.example.moviesland.Ui.image_result_zoom.ImageViewerActivity

class PersonProfilesImageAdapter(var mycontext : Activity,val result :List<PersonImagesProfiles>): RecyclerView.Adapter<PersonProfilesImageAdapter.MyViewHolder>()  {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val personprofile: ImageView = itemView.findViewById(R.id.profile_image)





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profiles_image_layut,parent , false)
        return PersonProfilesImageAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val personImagesProfiles: PersonImagesProfiles = result[position]
        var baseimageURL = "https://image.tmdb.org/t/p/w500"
        Glide.with(mycontext).load(baseimageURL+personImagesProfiles.file_path).into(holder.personprofile)
        holder.personprofile.setOnClickListener {

            val intent:Intent = Intent(mycontext,ImageViewerActivity::class.java)
            val compat:ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mycontext , holder.personprofile ,
                ViewCompat.getTransitionName(holder.personprofile)!!)
            intent.putExtra("image_uri",personImagesProfiles.file_path)
            mycontext.startActivity(intent,compat.toBundle() )

        }
    }

    override fun getItemCount(): Int { return result.size   }
}