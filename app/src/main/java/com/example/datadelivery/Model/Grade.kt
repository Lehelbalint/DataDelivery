package com.example.datadelivery

data class Grade(
    val data: List<Data_G>
)

data class Data_G(
    val id: Int,
    val attributes: Attributes_G
)

data class Attributes_G(
    val grade: Int,
    val percantage: Int,
    val final: Boolean,
    val createdAt: String,
    val date: String?,
    val course: Course_Grade,
    val student: Student_G,
    val teacher: Teacher_G,
    val department: Department_G,
    val note: String
)

data class Department_G(
    val data: DepartmentData_G
            )
data class Course_Grade(
    val data: CourseData_G
)

data class CourseData_G(
    val id: Int,
    val attributes: CourseAttributes_G
)
data class DepartmentData_G(
    val id: Int,
    val attributes: DepartmentAttributes_G
)

data class DepartmentAttributes_G(
    val department_name: String,
    val year: Int

)
data class CourseAttributes_G(
    val name: String ,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)

data class Student_G(
    val data: StudentData_G
)

data class StudentData_G(
    val id: Int,
    val attributes: StudentAttributes_G
)

data class StudentAttributes_G(
    val neptun_id: String,
    val email: String,
    val name: String,
    val password: String
)

data class Teacher_G(
    val data: TeacherData_G
)

data class TeacherData_G(
    val id: Int,
    val attributes: TeacherAttributes_G
)

data class TeacherAttributes_G(
    val name: String,
    val email: String,
)