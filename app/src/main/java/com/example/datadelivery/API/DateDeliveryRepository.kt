package com.example.datadelivery.API

import com.example.datadelivery.Student
import retrofit2.Response
import com.example.datadelivery.API.RetrofitInstance
import com.example.datadelivery.Course
import com.example.datadelivery.Grade
import com.example.datadelivery.Models.AddRatingRequest
import com.example.datadelivery.Models.AnswerMessage
import com.example.datadelivery.Models.Rating
import com.example.datadelivery.StudentData


class DateDeliveryRepository {
    suspend fun getStudents(): Response<StudentData> {
        return RetrofitInstance.api.getStudents()
    }
    suspend fun getCourses(): Response<Course>
    {
        return RetrofitInstance.api.getCourses()
    }
    suspend fun getGrades(): Response<Grade>
    {
        return RetrofitInstance.api.getGrades()
    }
    suspend fun getRatings(): Response<Rating>
    {
        return RetrofitInstance.api.getRatings()
    }
    suspend fun addRating(request: AddRatingRequest): Response<AnswerMessage>
    {
        return RetrofitInstance.api.addRating(request)
    }
}