package com.example.moviesland.Pojo.MoviesModel

data class MoviesCredts
    (
    val id:Int,
    val cast:List<MovieCreditsCast>,
    val crew :List<MovieCreditsCrew>
            )
{}