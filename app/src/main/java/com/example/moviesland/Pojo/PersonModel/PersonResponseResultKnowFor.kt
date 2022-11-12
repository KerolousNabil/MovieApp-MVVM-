package com.example.moviesland.Pojo.PersonModel

data class PersonResponseResultKnowFor (
            val vote_count:Int,
            val vote_average:Float,
            val id:Int,
            val video:Boolean,
            val media_type:String,
            val title:String,
            val popularity:String,
            val poster_path:String,
            val original_title:String,
            val original_language:String,
            val genre_ids:List<Int>,
            val backdrop_path:String,
            val adult:Boolean,
            val overview:String,
            val release_date:String


        ){
}