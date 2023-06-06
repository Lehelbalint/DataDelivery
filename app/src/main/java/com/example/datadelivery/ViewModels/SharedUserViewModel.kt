package com.example.datadelivery.ViewModels

import androidx.lifecycle.ViewModel
import com.example.datadelivery.Course
import com.example.datadelivery.Student

class SharedUserViewModel : ViewModel(){
    lateinit var currentUser : Student
    var position = 0
    lateinit var courseList : Course
    var allPosition = 0
}
