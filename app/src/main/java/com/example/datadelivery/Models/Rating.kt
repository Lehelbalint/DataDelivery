package com.example.datadelivery.Models

import java.time.LocalDateTime

data class Rating(
    val data: List<Data>
)

data class Data(
    val id: Int,
    val attributes: Attributes
)

data class Attributes(
    val interest_rate: Int,
    val relevance_rate: Int,
    val friendly_teacher_rate: Int,
    val fair_pointing_rate: Int,
    val material_availability_rate: Int,
    val timeschedule_rate: Int,
    val flexibility_rate: Int,
    val course_level: Int,
    val teacher_rate: Int,
    val final_course_rate: Int,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val course: Course,
    val student: Student
)

data class Course(
    val data: CourseData
)

data class CourseData(
    val id: Int,
    val attributes: CourseAttributes
)

data class CourseAttributes(
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)

data class Student(
    val data: StudentData
)

data class StudentData(
    val id: Int,
    val attributes: StudentAttributes
)

data class StudentAttributes(
    val neptun_id: String,
    val email: String,
    val name: String,
    val password: String
)
