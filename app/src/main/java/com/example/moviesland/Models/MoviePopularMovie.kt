package com.example.moviesland.Models

import android.util.Log
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

class MoviePopularMovie: ViewModel() {
    private var movieLiveData = MutableLiveData<List<MovieResponseResult>>()
    private var retrofitServices: RetrofitServices =   RetrofitClient.getClient().create(RetrofitServices::class.java)

        fun getPopularMovie()
        {
            val movieresponsecall : Call<MovieResponse> = retrofitServices.getMovieByPopular(
                BuildConfig.THE_MOVIE_DB_API_KEY)
            movieresponsecall.enqueue(object :Callback<MovieResponse>{
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>)
                {

                    movieLiveData.value =response.body()!!.results
                    //store the result in paper data base to access offline
                    val movieResponse: MovieResponse? = response.body()

                    Paper.book().write("cache",Gson().toJson(movieResponse))
                    //store category spinner when app start
                    Paper.book().write("source", "movie_popular")


                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        }
        fun getNextPageMoviePopular(page:Int)
        {
            val movieresponsecall : Call<MovieResponse> = retrofitServices.getNextMovieByPopular(
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
    fun observePopularMovieResponseModel() : LiveData<List<MovieResponseResult>> {
        return movieLiveData
    }
}