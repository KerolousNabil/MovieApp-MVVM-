package com.example.moviesland.Ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesland.BuildConfig
import com.example.moviesland.Client.RetrofitClient
import com.example.moviesland.Client.RetrofitServices
import com.example.moviesland.Models.*
import com.example.moviesland.Pojo.MoviesModel.MovieResponse
import com.example.moviesland.Pojo.MoviesModel.MovieResponseResult
import com.example.moviesland.Pojo.PersonModel.PersonResponse
import com.example.moviesland.Pojo.PersonModel.PersonResponseResult
import com.example.moviesland.R
import com.google.gson.Gson
import io.paperdb.Paper
import org.angmarch.views.NiceSpinner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var sourcespinner: NiceSpinner
    private lateinit var query:EditText
    private lateinit var search_button:Button
    private lateinit var recyclerview:RecyclerView
    private lateinit var retrofitServices: RetrofitServices
    private lateinit var modelpopularmovie:MoviePopularMovie
    private lateinit var modelnowplaying:MovieNowPlayingMovie
    private lateinit var modeltoprated:MovieTopRatedMovie
    private lateinit var modelupcoming:MovieUpcomingMovie
    private lateinit var moviesearchmodel:MovieSearchResponseModel
    private lateinit var personSearchModel: PersonSearchResponseModel

    var movieResponseResult : MutableList<MovieResponseResult> = mutableListOf<MovieResponseResult>()
    var personResponseResult : List<PersonResponseResult> = ArrayList<PersonResponseResult>()
    private var movieSearchAdapter: MovieSearchAdapter =  MovieSearchAdapter(this@MainActivity,movieResponseResult)
    private var personSearchAdapter:PersonSearchAdapter = PersonSearchAdapter(this@MainActivity,personResponseResult)
    private var PageNum = 1
    private lateinit var searchLayout:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        modelpopularmovie = ViewModelProviders.of(this).get(MoviePopularMovie::class.java)
        modelnowplaying = ViewModelProviders.of(this).get(MovieNowPlayingMovie::class.java)
        modeltoprated =  ViewModelProviders.of(this).get(MovieTopRatedMovie::class.java)
        modelupcoming =  ViewModelProviders.of(this).get(MovieUpcomingMovie::class.java)
        moviesearchmodel =  ViewModelProviders.of(this).get(MovieSearchResponseModel::class.java)
        personSearchModel =  ViewModelProviders.of(this).get(PersonSearchResponseModel::class.java)

        Paper.init(this)
        window.statusBarColor = this.resources.getColor(R.color.red)
        window.navigationBarColor = this.resources.getColor(R.color.red)

        searchLayout = findViewById(R.id.search_layout)
        sourcespinner = findViewById(R.id.spinner)
        query = findViewById(R.id.query_edit_text)
        search_button = findViewById(R.id.query_search_button)
        recyclerview = findViewById(R.id.result_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false)

      /*  modelpopularmovie.getPopularMovie()
        modelpopularmovie.observePopularMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                it->movieSearchAdapter.setMovieList(it)
        })*/



        retrofitServices = RetrofitClient.getClient().create(RetrofitServices::class.java)
        //list for spinner
        val category: List<String> = LinkedList(Arrays.asList("Get popular movies","Get top rated movies" ,"Get now playing movies","Get Upcoming movies","Search for any movie or actor"))
        sourcespinner.attachDataSource(category)



        //retrieve position when start and set the spinner
        if (Paper.book().read("position",true)!=null)
        {
            val position = Paper.book().read("position",sourcespinner.selectedIndex)
            sourcespinner.setSelectedIndex(position!!)

            if (position==4)
            {
                searchLayout.visibility =View.VISIBLE
            }
            else{
                searchLayout.visibility =View.GONE

            }
        }


        //retrieve result from paper db and start
        if (Paper.book().read("cache",true)!=null)
        {
            val results:String = Paper.book().read("cache",true).toString()
           if (Paper.book().read("source",true)!=null)
            {
                val source:String = Paper.book().read("source",true).toString()
                if (source.equals("movie"))
                {

                   val movieResponse: MovieResponse = Gson().fromJson(results, MovieResponse::class.java)
                    movieResponseResult = movieResponse.results as MutableList<MovieResponseResult>
                    movieSearchAdapter = MovieSearchAdapter(this,movieResponseResult)
                    recyclerview.adapter = movieSearchAdapter




                }
                else if (source.equals("person"))
                {
                  val personResponse: PersonResponse = Gson().fromJson(results, PersonResponse::class.java)
                     personResponseResult  = personResponse.results
                     personSearchAdapter = PersonSearchAdapter(this@MainActivity,personResponseResult)
                     recyclerview.adapter = personSearchAdapter



                }
                else if (source.equals("movie_popular"))
                {
                    val movieResponse: MovieResponse = Gson().fromJson(results, MovieResponse::class.java)
                    movieResponseResult = movieResponse.results as MutableList<MovieResponseResult>
                    movieSearchAdapter = MovieSearchAdapter(this,movieResponseResult)
                    recyclerview.adapter = movieSearchAdapter
                }
                else if (source.equals("movie_nowplaying"))
                {
                    val movieResponse: MovieResponse = Gson().fromJson(results, MovieResponse::class.java)
                    movieResponseResult = movieResponse.results as MutableList<MovieResponseResult>
                    movieSearchAdapter = MovieSearchAdapter(this,movieResponseResult)
                    recyclerview.adapter = movieSearchAdapter
                }

                else if (source.equals("movie_upcoming"))
                {
                    val movieResponse: MovieResponse = Gson().fromJson(results, MovieResponse::class.java)
                    movieResponseResult = movieResponse.results as MutableList<MovieResponseResult>
                    movieSearchAdapter = MovieSearchAdapter(this,movieResponseResult)
                    recyclerview.adapter = movieSearchAdapter
                }
                else if (source.equals("movie_toprated"))
                {
                    val movieResponse: MovieResponse = Gson().fromJson(results, MovieResponse::class.java)
                    movieResponseResult = movieResponse.results as MutableList<MovieResponseResult>
                    movieSearchAdapter = MovieSearchAdapter(this,movieResponseResult)
                    recyclerview.adapter = movieSearchAdapter
                }
        }
        sourcespinner.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            if (parent.selectedIndex ==0)
            {
                movieResponseResult.clear()
                recyclerview.adapter = movieSearchAdapter

                PageNum = 1
                searchLayout.visibility =View.GONE
                modelpopularmovie.getPopularMovie()
                modelpopularmovie.observePopularMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                        it->movieSearchAdapter.setMovieList(it)
                })

            }

             if (parent.selectedIndex==1)
            {
                movieResponseResult.clear()
                recyclerview.adapter = movieSearchAdapter
                PageNum = 1
                modeltoprated.getTopRaterMovie()
                modeltoprated.observeTopRatedMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                        it->movieSearchAdapter.setMovieList(it)
                })
                searchLayout.visibility =View.GONE




            }
            if (parent.selectedIndex==2)
            {
                movieResponseResult.clear()
                recyclerview.adapter = movieSearchAdapter
                PageNum = 1
                modelnowplaying.getNowPlayingMovie()
                modelnowplaying.observeNowPlayingMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                        it->movieSearchAdapter.setMovieList(it)
                })
                searchLayout.visibility =View.GONE


            }
            if (parent.selectedIndex==3)
            {
                movieResponseResult.clear()
                recyclerview.adapter = movieSearchAdapter
                PageNum = 1
                modelupcoming.getUpcomingMovie()
                modelupcoming.observeUpcomingMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                        it->movieSearchAdapter.setMovieList(it)
                })
                searchLayout.visibility =View.GONE

            }
            if (position==4)
            {
                searchLayout.visibility =View.VISIBLE

            }

            recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1))
                    {

                        if(parent.selectedIndex == 0){
                            modelpopularmovie.getNextPageMoviePopular(PageNum++)
                            modelpopularmovie.observePopularMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                                    it-> movieSearchAdapter.addMovielist(it)
                            })
                        }


                        else if(parent.selectedIndex == 1){
                            modeltoprated.getNextTopRaterMovie(PageNum++)
                            modeltoprated.observeTopRatedMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                                    it-> movieSearchAdapter.addMovielist(it)
                            })
                        }
                        else if(parent.selectedIndex==2) {
                            modelnowplaying.getNextNowPlayingMovie(PageNum++)
                            modelnowplaying.observeNowPlayingMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                                    it-> movieSearchAdapter.addMovielist(it)
                            })
                        }
                        else if(parent.selectedIndex==3) {
                            modelupcoming.getNextPageMovieUpcoming(PageNum++)
                            modelupcoming.observeUpcomingMovieResponseModel().observe(this@MainActivity, androidx.lifecycle.Observer {
                                    it-> movieSearchAdapter.addMovielist(it)
                            })
                        }

                    }
                } })


        }


             //get query from user
        search_button.setOnClickListener {
            if (query.text!=null)
            {
                val Query  = query.getText().toString()
                if (Query.equals("") || Query.equals("  "))
                {
                    Toast.makeText(this,"Please enter any text ", Toast.LENGTH_LONG).show()
                }
                else
                {
                    query.setText("")
                    //get category to search the query    movie or person
                    val queryfinal = Query.replace(" ", "+")
                         val movieresponsecall :Call<MovieResponse> = retrofitServices
                                .getMovieByQuery(BuildConfig.THE_MOVIE_DB_API_KEY,queryfinal)
                            movieresponsecall.enqueue(object :Callback<MovieResponse>{
                                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>)
                                {
                                    val movieResponse: MovieResponse? = response.body()
                                    if(movieResponse!=null)
                                    {
                                        val movieResponseResult :List<MovieResponseResult> = movieResponse.results
                                        movieSearchAdapter = MovieSearchAdapter(this@MainActivity,movieResponseResult)
                                        recyclerview.adapter = movieSearchAdapter

                                        //store the result in paper data base to access offline
                                        Paper.book().write("cache",Gson().toJson(movieResponse))
                                        //store category spinner when app start
                                        Paper.book().write("source", "movie")

                                    }
                                }

                                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                                    TODO("Not yet implemented")
                               }

                            })

                       val personresponsecall:Call<PersonResponse> = retrofitServices
                                .getPersonByQuery(BuildConfig.THE_MOVIE_DB_API_KEY,queryfinal)

                            personresponsecall.enqueue(object :Callback<PersonResponse>
                            {
                                override fun onResponse(call: Call<PersonResponse>, response: Response<PersonResponse>)
                                {
                                    val personResponse: PersonResponse? = response.body()
                                    if(personResponse!=null)
                                    {
                                        val personResponseResult :List<PersonResponseResult> = personResponse.results
                                        personSearchAdapter = PersonSearchAdapter(this@MainActivity,personResponseResult)
                                        recyclerview.adapter = personSearchAdapter

                                        //store the result in paper data base to access offline
                                        Paper.book().write("cache",Gson().toJson(personResponse))
                                        //store category spinner when app start
                                        Paper.book().write("source", "person")
                                }
                                }

                                override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                                    Log.d("error",t.message.toString())
                                }

                            })




                }
            }
        }

    }




}
    override fun onStop() {
        super.onStop()
        //set the position of spinner in offline to retrieve when start
        Paper.book().write("position",sourcespinner.selectedIndex)
    }
}
