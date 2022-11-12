package com.example.moviesland.Ui.person_data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesland.BuildConfig
import com.example.moviesland.Client.RetrofitClient
import com.example.moviesland.Client.RetrofitServices
import com.example.moviesland.Pojo.PersonModel.PersonDetails
import com.example.moviesland.Pojo.PersonModel.PersonImages
import com.example.moviesland.Pojo.PersonModel.PersonImagesProfiles
import com.example.moviesland.R
import com.flaviofaria.kenburnsview.KenBurnsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonDetailsActivity : AppCompatActivity() {
    private lateinit var retrofitServices: RetrofitServices
    private lateinit var persondataprofileImage: KenBurnsView
    private lateinit var person_detail_name:TextView


    private lateinit var person_detail_alsoknownaslayout:LinearLayoutCompat
    private lateinit var person_detail_birthdaylayout:LinearLayoutCompat
    private lateinit var person_detail_Deathlayout:LinearLayoutCompat
    private lateinit var person_detail_Departmentlayout:LinearLayoutCompat
    private lateinit var person_detail_Homepagelayout:LinearLayoutCompat
    private lateinit var person_detail_Biographylayouut : LinearLayoutCompat
    private lateinit var person_detail_PlaceOfBirthdaylayouut : LinearLayoutCompat

    private lateinit var person_detail_alsoknownas:TextView
    private lateinit var person_detail_birthday:TextView
    private lateinit var person_detail_Death:TextView
    private lateinit var person_detail_Department:TextView
    private lateinit var person_detail_Homepage:TextView
    private lateinit var person_detail_Biography : TextView
    private lateinit var person_detail_PlaceOfBirthday : TextView

    private lateinit var person_detail_profiles_images:RecyclerView
    private lateinit var person_detail_profiles_images_layout:LinearLayoutCompat

    private lateinit var personProfilesImageAdapter: PersonProfilesImageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_data)

        window.statusBarColor = this.resources.getColor(R.color.red)
        window.navigationBarColor = this.resources.getColor(R.color.red)

        retrofitServices = RetrofitClient.getClient().create(RetrofitServices::class.java)
        persondataprofileImage = findViewById(R.id.person_detail_diagona_image_view)
        person_detail_alsoknownaslayout = findViewById(R.id.person_detail_also_known_as_layout)
        person_detail_birthdaylayout = findViewById(R.id.person_detail_birthday_layout)
        person_detail_Deathlayout = findViewById(R.id.person_detail_deathday_layout)
        person_detail_Departmentlayout = findViewById(R.id.person_detail_known_for_department_layout)
        person_detail_Homepagelayout = findViewById(R.id.person_detail_homepage_layout)
        person_detail_Biographylayouut = findViewById(R.id.person_detail_biography_layout)
        person_detail_PlaceOfBirthdaylayouut = findViewById(R.id.person_detail_placeofbirth_layout)


        person_detail_name = findViewById(R.id.person_detail_name)
        person_detail_alsoknownas = findViewById(R.id.person_detail_also_known_as)
        person_detail_birthday = findViewById(R.id.person_detail_date_of_birth)
        person_detail_Death = findViewById(R.id.person_detail_deathday)
        person_detail_Department = findViewById(R.id.person_detail_known_for_department)
        person_detail_Homepage = findViewById(R.id.person_detail_homepage)
        person_detail_Biography = findViewById(R.id.person_detail_biography)
        person_detail_PlaceOfBirthday = findViewById(R.id.person_detail_place_of_bitrh)

        person_detail_profiles_images = findViewById(R.id.person_detail_profiles_images)
        person_detail_profiles_images_layout = findViewById(R.id.person_detail_profiles_images_layout)

        person_detail_profiles_images.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


        val intent:Intent = getIntent()
        if (intent!=null&& intent.extras!=null)
        {

            if (intent.extras!!.getString("id")!=null)
            {
                val id = (intent.extras!!.getString("id"))!!.toInt()
                val personDetailCall:Call<PersonDetails> = retrofitServices.getPersonDetailsById(id.toString(),BuildConfig.THE_MOVIE_DB_API_KEY)
                personDetailCall.enqueue(object :Callback<PersonDetails>{
                    override fun onResponse(call: Call<PersonDetails>, response: Response<PersonDetails>)
                    {
                        val personDetailresponse: PersonDetails? = response.body()
                        if (personDetailresponse!=null)
                        {
                            pereparePersonDetails(personDetailresponse)
                        }
                        else
                        {
                            Toast.makeText(this@PersonDetailsActivity,"Any details Are not found",Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<PersonDetails>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })

                val personImagescall:Call<PersonImages> =retrofitServices.getPersonImagesById(id.toString(),BuildConfig.THE_MOVIE_DB_API_KEY)
                personImagescall.enqueue(object :Callback<PersonImages>{
                    override fun onResponse(call: Call<PersonImages>, response: Response<PersonImages>) {


                        val personImages: PersonImages? = response.body()
                        val personImagesProfileslist :List<PersonImagesProfiles> = personImages!!.profiles

                        if (personImagesProfileslist!=null && personImagesProfileslist.size>0)
                        {
                            person_detail_profiles_images_layout.visibility = View.VISIBLE
                            personProfilesImageAdapter = PersonProfilesImageAdapter(this@PersonDetailsActivity,personImagesProfileslist)
                            person_detail_profiles_images.adapter= personProfilesImageAdapter

                        }
                        else
                        {
                            person_detail_profiles_images_layout.visibility = View.GONE

                        }

                    }

                    override fun onFailure(call: Call<PersonImages>, t: Throwable) {
                        TODO("Not yet implemented")
                    }


                })
            }
        }

    }

    private fun pereparePersonDetails(personDetailresponse: PersonDetails)
    {

        val profilepath = "https://image.tmdb.org/t/p/w500"+personDetailresponse.profile_path
        val name = personDetailresponse.name
        val birthday = personDetailresponse.birthday
        val placeofbirthday = personDetailresponse.place_of_birth
        val deathdate = personDetailresponse.deathday
        val department = personDetailresponse.known_for_department
        val homepage = personDetailresponse.homepage
        val biography = personDetailresponse.biography
        val alsopknownlist:List<String> = personDetailresponse.also_known_as

        Glide.with(this).load(profilepath).into(persondataprofileImage)

        if (name!=null)
        {
            if (name.length>0)
            {
                person_detail_name.text = name
                person_detail_name.visibility = View.VISIBLE

            }
            else
            {
                person_detail_name.visibility = View.GONE

            }
        }
        else
        {
            person_detail_name.visibility = View.GONE

        }
        if (birthday!=null)
        {
            if (birthday.length>0)
            {
                person_detail_birthday.text = birthday
                person_detail_birthdaylayout.visibility = View.VISIBLE

            }
            else
            {
                person_detail_birthdaylayout.visibility = View.GONE

            }
        }
        else
        {
            person_detail_birthdaylayout.visibility = View.GONE

        }

        if (placeofbirthday!=null)
        {
            if (placeofbirthday.length>0)
            {
                person_detail_PlaceOfBirthday.text = placeofbirthday
                person_detail_PlaceOfBirthdaylayouut.visibility = View.VISIBLE

            }
            else
            {
                person_detail_PlaceOfBirthdaylayouut.visibility = View.GONE

            }
        }
        else
        {
            person_detail_PlaceOfBirthdaylayouut.visibility = View.GONE

        }

        if (deathdate!=null)
        {
            if (deathdate.length>0)
            {
                person_detail_Death.text = deathdate
                person_detail_Deathlayout.visibility = View.VISIBLE

            }
            else
            {
                person_detail_Deathlayout.visibility = View.GONE

            }
        }
        else
        {
            person_detail_Deathlayout.visibility = View.GONE

        }

        if (department!=null)
        {
            if (department.length>0)
            {
                person_detail_Department.text = department
                person_detail_Departmentlayout.visibility = View.VISIBLE

            }
            else
            {
                person_detail_Departmentlayout.visibility = View.GONE

            }
        }
        else
        {
            person_detail_Departmentlayout.visibility = View.GONE

        }

        if (homepage!=null)
        {
            if (homepage.length>0)
            {
                person_detail_Homepage.text = homepage
                person_detail_Homepagelayout.visibility = View.VISIBLE

            }
            else
            {
                person_detail_Homepagelayout.visibility = View.GONE

            }
        }
        else
        {
            person_detail_Homepagelayout.visibility = View.GONE

        }

        if (biography!=null)
        {
            if (biography.length>0)
            {
                person_detail_Biography.text = biography
                person_detail_Biographylayouut.visibility = View.VISIBLE

            }
            else
            {
                person_detail_Biographylayouut.visibility = View.GONE

            }
        }
        else
        {
            person_detail_Biographylayouut.visibility = View.GONE

        }

        if (alsopknownlist!=null)
        {
            if (alsopknownlist.size>0)
            {
                val stringBuilder:StringBuilder = StringBuilder()
                for (i in alsopknownlist.indices)
                {
                    if (i == alsopknownlist.size -1)
                    {
                        stringBuilder.append(alsopknownlist.get(i))
                    }
                    else
                    {
                        stringBuilder.append(alsopknownlist.get(i)).append(".")
                    }
                }
                person_detail_alsoknownas.text = stringBuilder.toString()
                person_detail_alsoknownaslayout.visibility = View.VISIBLE
            }
            else
            {
                person_detail_alsoknownaslayout.visibility = View.GONE

            }
        }
        else
        {
            person_detail_alsoknownaslayout.visibility = View.GONE

        }

    }
}