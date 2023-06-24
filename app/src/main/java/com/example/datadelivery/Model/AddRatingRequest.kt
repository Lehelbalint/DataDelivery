package com.example.datadelivery.Model

data class AddRatingRequest(
    val data: Data
) {
    data class Data(
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
        val course: Course,
        val student: Student
    ) {
        data class Course(
            val connect: List<Int>
        )

        data class Student(
            val connect: List<Int>
        )
    }
}