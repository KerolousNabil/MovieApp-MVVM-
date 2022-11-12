package com.example.moviesland.Client

import com.example.moviesland.Pojo.MoviesModel.MovieDetails
import com.example.moviesland.Pojo.MoviesModel.MovieResponse
import com.example.moviesland.Pojo.MoviesModel.MoviesCredts
import com.example.moviesland.Pojo.PersonModel.PersonDetails
import com.example.moviesland.Pojo.PersonModel.PersonImages
import com.example.moviesland.Pojo.PersonModel.PersonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {
    @GET("/3/search/movie")
    fun getMovieByQuery(  @Query("api_key") api_key:String ,@Query("query") query:String ): Call<MovieResponse>
    @GET("/3/movie/{movie_id}")
    fun getMovieDetailsById( @Path("movie_id") movie_id:Int, @Query("api_key") api_key:String  ): Call<MovieDetails>
    @GET("/3/movie/{movie_id}/credits")
    fun getMovieCreditsById( @Path("movie_id") movie_id:Int, @Query("api_key") api_key:String  ): Call<MoviesCredts>
    @GET("/3/search/person")
    fun getPersonByQuery( @Query("api_key") api_key:String ,@Query("query") query:String ): Call<PersonResponse>
    @GET("/3/person/{person_id}")
    fun getPersonDetailsById( @Path("person_id") person_id:String, @Query("api_key") api_key:String  ): Call<PersonDetails>
    @GET("/3/person/{person_id}/images")
    fun getPersonImagesById( @Path("person_id") person_id:String, @Query("api_key") api_key:String  ): Call<PersonImages>


    @GET("/3/movie/top_rated")
    fun getMovieByToprated(@Query("api_key") api_key:String):Call<MovieResponse>

    @GET("/3/movie/top_rated")
    fun getNextMovieByToprated(@Query("api_key") api_key:String, @Query("page") page:Int):Call<MovieResponse>


    @GET("/3/movie/popular")
    fun getMovieByPopular(@Query("api_key") api_key:String):Call<MovieResponse>

    @GET("/3/movie/popular")
    fun getNextMovieByPopular(@Query("api_key") api_key:String, @Query("page") page:Int):Call<MovieResponse>


    @GET("/3/movie/now_playing")
    fun getMovieByNowPlaying(@Query("api_key") api_key:String):Call<MovieResponse>
    @GET("/3/movie/now_playing")
    fun getNextMovieByNowPlaying(@Query("api_key") api_key:String, @Query("page") page:Int):Call<MovieResponse>

    @GET("/3/movie/upcoming")
    fun getMovieByUpcoming(@Query("api_key") api_key:String):Call<MovieResponse>
    @GET("/3/movie/upcoming")
    fun getNextMovieByUpcoming(@Query("api_key") api_key:String, @Query("page") page:Int):Call<MovieResponse>

}