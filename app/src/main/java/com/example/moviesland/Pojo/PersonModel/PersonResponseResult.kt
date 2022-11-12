package com.example.moviesland.Pojo.PersonModel

data class PersonResponseResult(
    val popularity:Double,
    val id:Int,
    val profile_path:String,
    val name:String,
    val known_for:List<PersonResponseResultKnowFor>
) {

}