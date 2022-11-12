package com.example.moviesland.Ui.movie_data

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
import com.example.moviesland.Pojo.MoviesModel.*
import com.example.moviesland.R
import com.flaviofaria.kenburnsview.KenBurnsView
import com.github.lzyzsd.circleprogress.ArcProgress
import com.github.lzyzsd.circleprogress.CircleProgress
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var retrofitServices: RetrofitServices

    private lateinit var movieDetailsposterImage:KenBurnsView
    private lateinit var movieDetailsbackdropPosterCircle:CircleImageView
    private lateinit var movieRating:ArcProgress

    private lateinit var movieDetailsOriginTitleLayout:LinearLayoutCompat
    private lateinit var movieDetailsOriginLanguageLayout:LinearLayoutCompat
    private lateinit var movieDetailsAdultLayout:LinearLayoutCompat
    private lateinit var movieDetailsStatusLayout:LinearLayoutCompat
    private lateinit var movieDetailsRuntimeLayout:LinearLayoutCompat
    private lateinit var movieDetailsBudgedLayout:LinearLayoutCompat
    private lateinit var movieDetailsRevenueLayout:LinearLayoutCompat
    private lateinit var movieDetailsGenresLayout:LinearLayoutCompat
    private lateinit var movieDetailsProductionCountriesLayout:LinearLayoutCompat
    private lateinit var movieDetailsReleaseDateLayout:LinearLayoutCompat
    private lateinit var movieDetailsHomepageLayout:LinearLayoutCompat
    private lateinit var movieDetailsOverviewLayout:LinearLayoutCompat


    private lateinit var movieDetailstitle:TextView
    private lateinit var movieDetailsOriginTitle:TextView
    private lateinit var movieDetailsOriginLanguage:TextView
    private lateinit var movieDetailsAdult:TextView
    private lateinit var movieDetailsStatus:TextView
    private lateinit var movieDetailsRuntime:TextView
    private lateinit var movieDetailsBudged:TextView
    private lateinit var movieDetailsRevenue:TextView
    private lateinit var movieDetailsGenres:TextView
    private lateinit var movieDetailsProductionCountries:TextView
    private lateinit var movieDetailsReleaseDate:TextView
    private lateinit var movieDetailsHomepage:TextView
    private lateinit var movieDetailsOverview:TextView


    private lateinit var movieDetailsCastRecyvlerViewLayout:LinearLayoutCompat
    private lateinit var movieDetailsCrewRecyvlerViewLayout:LinearLayoutCompat
    private lateinit var movieDetailProductionCoumpanyRecyvlerViewLayout:LinearLayoutCompat

    private lateinit var movieDetailsCastRecyvlerView:RecyclerView
    private lateinit var movieDetailsCrewRecyvlerView:RecyclerView
    private lateinit var movieDetailProductionCoumpanyRecyvlerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        overridePendingTransition(R.anim. slideonright, R.anim.slideonleft);

        window.statusBarColor = this.resources.getColor(R.color.red)
        window.navigationBarColor = this.resources.getColor(R.color.red)

        retrofitServices = RetrofitClient.getClient().create(RetrofitServices::class.java)

        movieDetailsOriginTitleLayout=findViewById(R.id.movie_detail_original_title_layout)
        movieDetailsOriginLanguageLayout=findViewById(R.id.movie_detail_original_language_layout)
        movieDetailsAdultLayout=findViewById(R.id.movie_detail_original_adult_layout)
        movieDetailsStatusLayout=findViewById(R.id.movie_detail_status_layout)
        movieDetailsRuntimeLayout=findViewById(R.id.movie_detail_runtime_layout)
        movieDetailsBudgedLayout=findViewById(R.id.movie_detail_budged_layout)
        movieDetailsRevenueLayout=findViewById(R.id.movie_detail_revenue_layout)
        movieDetailsGenresLayout=findViewById(R.id.movie_detail_genre_layout)
        movieDetailsProductionCountriesLayout=findViewById(R.id.movie_detail_production_country_layout)
        movieDetailsReleaseDateLayout=findViewById(R.id.movie_detail_release_date_layout)
        movieDetailsHomepageLayout=findViewById(R.id.movie_detail_homepage_layout)
        movieDetailsOverviewLayout=findViewById(R.id.movie_detail_overview_layout)



        movieDetailsOriginTitle=findViewById(R.id.movie_detail_original_title)
        movieDetailsOriginLanguage=findViewById(R.id.movie_detail_original_language)
        movieDetailsAdult=findViewById(R.id.movie_detail_original_adult)
        movieDetailsStatus=findViewById(R.id.movie_detail_status)
        movieDetailsRuntime=findViewById(R.id.movie_detail_runtime)
        movieDetailsBudged=findViewById(R.id.movie_detail_budged)
        movieDetailsRevenue=findViewById(R.id.movie_detail_revenue)
        movieDetailsGenres=findViewById(R.id.movie_detail_genre)
        movieDetailsProductionCountries=findViewById(R.id.movie_detail_production_country)
        movieDetailsReleaseDate=findViewById(R.id.movie_detail_release_date)
        movieDetailsHomepage=findViewById(R.id.movie_detail_homepage)
        movieDetailsOverview=findViewById(R.id.movie_detail_overview)


        movieDetailsposterImage =findViewById(R.id.movie_detail_poster_image_view)
        movieDetailsbackdropPosterCircle = findViewById(R.id.movie_detail_poster_circle_image_view)
        movieRating = findViewById(R.id.movie_detail_rating_bar)
        movieDetailstitle= findViewById(R.id.movie_detail_title)


        movieDetailsCastRecyvlerView = findViewById(R.id.movie_detail_cast_recycler_ciew)
        movieDetailsCastRecyvlerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        movieDetailsCrewRecyvlerView = findViewById(R.id.movie_detail_crew_recycler_ciew)
        movieDetailsCrewRecyvlerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        movieDetailsCastRecyvlerViewLayout = findViewById(R.id.movie_detail_cast_layout)
        movieDetailsCrewRecyvlerViewLayout =findViewById(R.id.movie_detail_crew_layout)

        movieDetailProductionCoumpanyRecyvlerViewLayout = findViewById(R.id.movie_detail_ProductionCompanyRecycler_layout)
        movieDetailProductionCoumpanyRecyvlerView = findViewById(R.id.movie_detail_ProductionCompany_recyclerview)
        movieDetailProductionCoumpanyRecyvlerView.layoutManager =   LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val intent:Intent = getIntent()

        if (intent!=null &&intent.extras!=null)
        {
            if (intent.extras!!.getString("id")!=null)
            {
                val id = (intent.extras!!.getString("id"))!!.toInt()

                val movieDetalsCall: Call<MovieDetails> = retrofitServices.getMovieDetailsById(id, BuildConfig.THE_MOVIE_DB_API_KEY)
              
                movieDetalsCall.enqueue(object :Callback<MovieDetails>{
                    override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                        val movieDetailsresponse :MovieDetails? = response.body()
                        if (movieDetailsresponse!=null)
                        {
                            perepareMovieDetails(movieDetailsresponse)

                        }
                        else
                        {
                            Toast.makeText(this@MovieDetailsActivity,"Any details Are not found", Toast.LENGTH_LONG).show()

                        }
                    }

                    override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                        Toast.makeText(this@MovieDetailsActivity,"Any details Are not found", Toast.LENGTH_LONG).show()
                    }


                })





                val movieCreditsCall: Call<MoviesCredts> = retrofitServices.getMovieCreditsById(id, BuildConfig.THE_MOVIE_DB_API_KEY)

                movieCreditsCall.enqueue(object :Callback<MoviesCredts>{
                    override fun onResponse(call: Call<MoviesCredts>, response: Response<MoviesCredts>) {
                        val movieCreditsresponse :MoviesCredts? = response.body()
                        if (movieCreditsresponse!=null)
                        {
                            val movieCreditsCastList:List<MovieCreditsCast> = movieCreditsresponse.cast
                            if (movieCreditsCastList!=null&&movieCreditsCastList.size>0)
                            {
                                val movieCreditsCastAdapter = MovieCreditsCastAdapter(this@MovieDetailsActivity,movieCreditsCastList)
                                movieDetailsCastRecyvlerView.adapter = movieCreditsCastAdapter
                                movieDetailsCastRecyvlerViewLayout.visibility = View.VISIBLE
                            }
                            else
                            {
                                movieDetailsCastRecyvlerViewLayout.visibility = View.GONE

                            }


                            val movieCreditsCrewList:List<MovieCreditsCrew> = movieCreditsresponse.crew
                            if (movieCreditsCrewList!=null&&movieCreditsCrewList.size>0)
                            {
                                val movieCreditsCrewAdapter = MovieCreditsCrewAdapter(this@MovieDetailsActivity,movieCreditsCrewList)
                                movieDetailsCrewRecyvlerView.adapter = movieCreditsCrewAdapter
                                movieDetailsCrewRecyvlerViewLayout.visibility = View.VISIBLE
                            }
                            else
                            {
                                movieDetailsCrewRecyvlerViewLayout.visibility = View.GONE

                            }



                        }
                        else
                        {
                            movieDetailsCrewRecyvlerViewLayout.visibility = View.GONE
                            movieDetailsCastRecyvlerViewLayout.visibility = View.GONE

                            Toast.makeText(this@MovieDetailsActivity,"Any details Are not found", Toast.LENGTH_LONG).show()

                        }
                    }

                    override fun onFailure(call: Call<MoviesCredts>, t: Throwable) {
                        Toast.makeText(this@MovieDetailsActivity,"Any details Are not found", Toast.LENGTH_LONG).show()
                    }


                })


            }
        }
    }

    private fun perepareMovieDetails(movieDetailsresponse: MovieDetails)
    {
        val voteAverage = movieDetailsresponse.vote_average * 10
        val BASE_URI="https://image.tmdb.org/t/p/w500"
        val posterPath = movieDetailsresponse.poster_path
        val backdropPoster = movieDetailsresponse.backdrop_path
        val title = movieDetailsresponse.title
        val originalTitle = movieDetailsresponse.original_title
        val originalLanguage= movieDetailsresponse.original_language
        val adult = movieDetailsresponse.adult
        val status = movieDetailsresponse.status
        val runtime = movieDetailsresponse.runtime
        val budget = movieDetailsresponse.budget
        val revenue = movieDetailsresponse.revenue
        val movieDetailGenresList:List<MovieDetailsGenres> = movieDetailsresponse.generes
        val movieDetailProductionCounties:List<MovieDetailsProductionCountries> = movieDetailsresponse.production_countries
        val movieDetailProductionCompanies:List<MovieDetailsProductionCompanies> = movieDetailsresponse.production_companies

        val releasedate = movieDetailsresponse.release_date
        val homepage = movieDetailsresponse.homepage
        val overview = movieDetailsresponse.overview


        val rating = voteAverage.toInt()
        movieRating.progress = rating.toFloat()
        Glide.with(this).load(BASE_URI+posterPath).into(movieDetailsposterImage)
        Glide.with(this).load(BASE_URI+backdropPoster).into(movieDetailsbackdropPosterCircle)
        movieDetailstitle.text = title

        if (originalTitle!=null)
        {
            if (originalTitle.length>0)
            {
                movieDetailsOriginTitle.text = originalTitle
                movieDetailsOriginTitleLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsOriginTitleLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsOriginTitleLayout.visibility = View.GONE

        }


        if (originalLanguage!=null)
        {
            if (originalLanguage.length>0)
            {
                movieDetailsOriginLanguage.text = originalLanguage
                movieDetailsOriginLanguageLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsOriginLanguageLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsOriginLanguageLayout.visibility = View.GONE

        }


        if (adult)
        {

           movieDetailsAdult.text = "yes"
            movieDetailsAdultLayout.visibility = View.VISIBLE
        }
        else
        {
            movieDetailsAdult.text = "No"

            movieDetailsAdultLayout.visibility = View.VISIBLE

        }


        if (status!=null)
        {
            if (status.length>0)
            {
                movieDetailsStatus.text = status
                movieDetailsStatusLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsStatusLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsStatusLayout.visibility = View.GONE

        }


        if (runtime!=null)
        {
            if (runtime!=0)
            {
                movieDetailsRuntime.text = String.valueOf(runtime)
                movieDetailsRuntimeLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsRuntimeLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsRuntimeLayout.visibility = View.GONE

        }


        if (budget!=null)
        {
            if (budget!=0)
            {
                movieDetailsBudged.text = String.valueOf(budget)
                movieDetailsBudgedLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsBudgedLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsBudgedLayout.visibility = View.GONE

        }




        if (revenue!=null)
        {
            if (revenue!=0)
            {
                movieDetailsRevenue.text = String.valueOf(revenue)
                movieDetailsRevenueLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsRevenueLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsRevenueLayout.visibility = View.GONE

        }

        if (movieDetailGenresList!=null &&movieDetailGenresList.size>0)
        {
            val stringBuilder:StringBuilder = StringBuilder()
            for (i in 0 until movieDetailGenresList.size)
            {
                if (i ==movieDetailGenresList.size-1)
                {
                    stringBuilder.append(movieDetailGenresList.get(i).name)
                }
                else
                {
                    stringBuilder.append(movieDetailGenresList.get(i).name).append(". ")

                }

            }
            movieDetailsGenres.text = stringBuilder.toString()
            movieDetailsGenresLayout.visibility = View.VISIBLE
        }
        else
        {
            movieDetailsGenresLayout.visibility = View.GONE

        }

        if (movieDetailProductionCounties!=null &&movieDetailProductionCounties.size>0)
        {
            val stringBuilder:StringBuilder = StringBuilder()
            for (i in 0 until movieDetailProductionCounties.size)
            {
                if (i ==movieDetailProductionCounties.size-1)
                {
                    stringBuilder.append(movieDetailProductionCounties.get(i).name)
                }
                else
                {
                    stringBuilder.append(movieDetailProductionCounties.get(i).name).append(". ")

                }
            }
            movieDetailsProductionCountries.text = stringBuilder.toString()
            movieDetailsProductionCountriesLayout.visibility = View.VISIBLE
        }
        else
        {
            movieDetailsProductionCountriesLayout.visibility = View.GONE

        }


        if (releasedate!=null)
        {
            if (releasedate.length>0)
            {
                movieDetailsReleaseDate.text = String.valueOf(releasedate)
                movieDetailsReleaseDateLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsReleaseDateLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsReleaseDateLayout.visibility = View.GONE

        }



        if (homepage!=null)
        {
            if (homepage.length>0)
            {
                movieDetailsHomepage.text = String.valueOf(homepage)
                movieDetailsHomepageLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsHomepageLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsHomepageLayout.visibility = View.GONE

        }

        if (overview!=null)
        {
            if (overview.length>0)
            {
                movieDetailsOverview.text = String.valueOf(overview)
                movieDetailsOverviewLayout.visibility = View.VISIBLE

            }
            else
            {
                movieDetailsOverviewLayout.visibility = View.GONE

            }
        }
        else
        {
            movieDetailsOverviewLayout.visibility = View.GONE

        }

        if (movieDetailProductionCompanies!=null&&movieDetailProductionCompanies.size>0)
        {
            val movieCreditsProductionCompaniesAdapter = MovieCreditsProductionCompaniesAdapter   (this@MovieDetailsActivity,movieDetailProductionCompanies)
            movieDetailProductionCoumpanyRecyvlerView.adapter = movieCreditsProductionCompaniesAdapter
            movieDetailProductionCoumpanyRecyvlerViewLayout.visibility = View.VISIBLE
        }
        else
        {
            movieDetailProductionCoumpanyRecyvlerViewLayout.visibility = View.GONE

        }


    }


}