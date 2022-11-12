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

class MovieSearchResponseModel : ViewModel() {

    private var movieLiveData = MutableLiveData<List<MovieResponseResult>>()
    private var retrofitServices: RetrofitServices =   RetrofitClient.getClient().create(RetrofitServices::class.java)

    fun getMovieBySearch(queryfinal:String)
        {
            val movieresponsecall : Call<MovieResponse> = retrofitServices.getMovieByQuery(BuildConfig.THE_MOVIE_DB_API_KEY, queryfinal)
            movieresponsecall.enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    movieLiveData.value = response.body()!!.results
                    val movieResponse: MovieResponse? = response.body()
                    //store the result in paper data base to access offline
                    Paper.book().write("cache",Gson().toJson(movieResponse))
                    //store category spinner when app start
                    Paper.book().write("source", "movie")


                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    fun observeMovieSearchResponseModel() : LiveData<List<MovieResponseResult>> {
        return movieLiveData
    }




}