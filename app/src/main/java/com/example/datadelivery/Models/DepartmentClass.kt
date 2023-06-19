package com.example.datadelivery.Models

data class DepartmentClass(
    val data: List<DepartmentData>
)

data class DepartmentData(
    val id: Int,
    val attributes: DepartmentAttributes
)

data class DepartmentAttributes(
    val department_name: String,
    val year: Int
)
