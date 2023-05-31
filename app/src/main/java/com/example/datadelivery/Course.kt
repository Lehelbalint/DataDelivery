package com.example.datadelivery

data class Course(
    val data: List<CourseDataItem>
)

data class CourseDataItem(
    val id: Int,
    val attributes: CourseDataAttributes
)

data class CourseDataAttributes(
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val teachers: Teachers
) {
    data class Teachers(
        val data: List<TeacherItem>
    )

    data class TeacherItem(
        val id: Int,
        val attributes: TeacherAttributes
    )

    data class TeacherAttributes(
        val name: String,
        val email: String,
        val createdAt: String,
        val updatedAt: String,
        val publishedAt: String
    )
}