package com.chirikualii.materiapi.data.repository

import android.util.Log
import com.chirikualii.materiapi.data.model.Movie
import com.chirikualii.materiapi.data.remote.ApiService
import com.google.gson.Gson

class MovieRepoImpl(private val service: ApiService) : MovieRepo {
    private val TAG: String = "com.chirikualii.materiapi.data.repository"

    override suspend fun getPopularMovie(): List<Movie> {
        try {
            val response = service.getPopularMovie()

            if (response.isSuccessful){
                val listMovie = response.body()

                val listData = listMovie?.results?.map {
                    Movie(
                        title = it.title,
                        genre = it.releaseDate,
                        imagePoster = it.posterPath
                    )
                }
                Log.d(TAG, "getPopulerMovie: ${Gson().toJsonTree(listData)}")
                return listData ?: emptyList()
            }else{
                Log.e(TAG, "getPopularMovie eror code: ${response.code()} ")
                return emptyList()
            }
        }catch (msg: Exception){
            Log.e(TAG, "getPopulerMovie eror: ${msg.message}" )
            return emptyList()
        }
    }
}