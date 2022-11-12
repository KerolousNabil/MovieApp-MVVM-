package com.example.moviesland.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesland.BuildConfig
import com.example.moviesland.Client.RetrofitClient
import com.example.moviesland.Client.RetrofitServices
import com.example.moviesland.Pojo.MoviesModel.MovieResponse
import com.example.moviesland.Pojo.MoviesModel.MovieResponseResult
import com.google.gson.Gson
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieUpcomingMovie: ViewModel() {
    private var movieLiveData = MutableLiveData<List<MovieResponseResult>>()
    private var retrofitServices: RetrofitServices =   RetrofitClient.getClient().create(RetrofitServices::class.java)

        fun getUpcomingMovie()
        {
            val movieresponsecall : Call<MovieResponse> = retrofitServices.getMovieByUpcoming(
                BuildConfig.THE_MOVIE_DB_API_KEY)
            movieresponsecall.enqueue(object :Callback<MovieResponse>{
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>)
                {
                    movieLiveData.value =response.body()!!.results
                    val movieResponse: MovieResponse? = response.body()

                    Paper.book().write("cache",Gson().toJson(movieResponse))
                    //store category spinner when app start
                    Paper.book().write("source", "movie_upcoming")


                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        }
        fun getNextPageMovieUpcoming(page:Int)
        {
            val movieresponsecall : Call<MovieResponse> = retrofitServices.getNextMovieByUpcoming(
                BuildConfig.THE_MOVIE_DB_API_KEY,page)
            movieresponsecall.enqueue(object :Callback<MovieResponse>{
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>)
                {

                    movieLiveData.value = listOf( movieLiveData.value  as List<MovieResponseResult> ,response.body()!!.results ).flatten()


                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    fun observeUpcomingMovieResponseModel() : LiveData<List<MovieResponseResult>> {
        return movieLiveData
    }
}