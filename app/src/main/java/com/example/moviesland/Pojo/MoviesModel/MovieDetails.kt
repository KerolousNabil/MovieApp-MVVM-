package com.example.moviesland.Pojo.MoviesModel

data class MovieDetails(
   val adult:Boolean,
   val backdrop_path:String,
   val belongs_to_collection: MovieDetailsBelongsToCollection,
   val budget:Int,
   val generes:List<MovieDetailsGenres>,
   val homepage:String,
   val id:Int,
   val original_language:String,
   val original_title:String,
   val overview:String,
   val popularity:String,
   val poster_path:String,
   val production_companies:List<MovieDetailsProductionCompanies>,
   val production_countries:List<MovieDetailsProductionCountries>,
   val release_date:String,
   val revenue:Int,
   val runtime: Int,
   val spoken_languages:List<MovieDetailsSpokenLanguage>,
   val status :String,
   val tagline:String,
   val title:String,
   val video:Boolean,
   val vote_average:Double,
   val vote_count:Int,


   ) {}