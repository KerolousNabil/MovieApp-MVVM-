package com.example.moviesland.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesland.BuildConfig
import com.example.moviesland.Client.RetrofitClient
import com.example.moviesland.Client.RetrofitServices
import com.example.moviesland.Pojo.PersonModel.PersonResponse
import com.example.moviesland.Pojo.PersonModel.PersonResponseResult
import com.google.gson.Gson
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonSearchResponseModel : ViewModel() {

    private var personLiveData = MutableLiveData<List<PersonResponseResult>>()
    private var retrofitServices: RetrofitServices =   RetrofitClient.getClient().create(RetrofitServices::class.java)

    fun getPersonBySearch(queryfinal:String)
        {
            val personresponsecall : Call<PersonResponse> = retrofitServices.getPersonByQuery(BuildConfig.THE_MOVIE_DB_API_KEY, queryfinal)
            personresponsecall.enqueue(object : Callback<PersonResponse> {
                override fun onResponse(call: Call<PersonResponse>, response: Response<PersonResponse>) {
                    personLiveData.value = response.body()!!.results
                    val personResponse: PersonResponse? = response.body()
                    //store the result in paper data base to access offline
                    Paper.book().write("cache",Gson().toJson(personResponse))
                    //store category spinner when app start
                    Paper.book().write("source", "person")


                }

                override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    fun ObservePersonSearchResponseModel() : LiveData<List<PersonResponseResult>> {
        return personLiveData
    }




}