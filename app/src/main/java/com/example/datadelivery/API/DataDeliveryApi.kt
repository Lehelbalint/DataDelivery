package com.example.datadelivery.API


import com.example.datadelivery.Course
import com.example.datadelivery.Grade
import com.example.datadelivery.Models.AddRatingRequest
import com.example.datadelivery.Models.AnswerMessage
import com.example.datadelivery.Models.Rating
import com.example.datadelivery.Student
import com.example.datadelivery.StudentData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DataDeliveryApi {
    @GET(Constants.GET_STUDENTS_URL)
    suspend fun getStudents(): Response<StudentData>
    @GET(Constants.GET_COURSES_URL)
    suspend fun getCourses(): Response<Course>
    @GET(Constants.GET_GRADES_URL)
    suspend fun  getGrades(): Response<Grade>
    @GET(Constants.GET_RATINGS_URL)
    suspend fun  getRatings(): Response<Rating>
    @POST(Constants.POST_RATINGS_URL)
    suspend fun  addRating(@Body request: AddRatingRequest) : Response<AnswerMessage>

}