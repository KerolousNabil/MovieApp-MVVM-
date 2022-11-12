package com.example.moviesland.Pojo.PersonModel

data class PersonResponse(
    val page:Int,
    val total_results:Int,
    val total_pages:Int,
    val results:List<PersonResponseResult>,


    ) {
}