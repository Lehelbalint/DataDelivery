package com.example.datadelivery

import androidx.lifecycle.ViewModel

class SharedUserViewModel : ViewModel(){
    lateinit var currentUser : Student
    var position = 0
    lateinit var courseList : Course
    var allPosition = 0
}
