package com.example.datadelivery

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Student(
    val id: Int,
    val attributes: Attributes
)

data class Attributes(
    val neptun_id: String,
    val email: String,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val password: String,
    val department: Department,
    val grades: Grades,
    val courses: Courses
)

data class Department(
    val data: Data ?
)

data class Data(
    val id: Int,
    val attributes: DepartmentAttributes
)

data class DepartmentAttributes(
    val department_name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val year: Int
)

data class Grades(
    val data: List<DataItem>
)

data class DataItem(
    val id: Int,
    val attributes: GradeAttributes
)

data class GradeAttributes(
    val grade: Int,
    val percentage: Int ?,
    val final: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val date: String?
)

data class Courses(
    val data: List<CourseItem>
)

data class CourseItem(
    val id: Int,
    val attributes: CourseAttributes
)

data class CourseAttributes(
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)
data class StudentData(
    val data: List<Student>
)