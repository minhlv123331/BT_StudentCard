package com.example.viewbinding.network

import retrofit2.http.GET

interface StudentApiService {
    @GET("users")
    suspend fun getStudents(): List<StudentDto>
}