package com.example.retrofitexample.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {

    /** (1)
    https://rickandmortyapi.com/api/character?page=1
    the retrofit builder will need the base url so we extract that from our
    link and create the base url variable of type string
     */


    private val BASE_URL = "https://rickandmortyapi.com/api/"

    /** (2)
    Next we create the variable for the moshi builder,
    adding a converter to it
     */

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /** (3)
    Then we create an instance of Retrofit by lazy
    so it can initialized only when it is needed
    pass the base url and the moshi variables created above
     */

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /** (5)
     Create an instance of interface
     */

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }


}

/** (4)
Create an interface below the object class to define how
Retrofit talks to the service using the Get method
 */

//An Interface called ApiService
interface ApiService {

    /**
    Then we create a fetchCharacters method
    Annotate with @Get method passing the character path just like in our api link
    Above to tell the server send us those characters
     */

    @GET("character")
    suspend fun fetchCharacters(@Query("page") page: String): CharacterResponse

}