package com.example.datadelivery.ViewModel

import androidx.lifecycle.ViewModel
import com.example.datadelivery.Course
import com.example.datadelivery.Student

class SharedUserViewModel : ViewModel(){
    lateinit var currentUser : Student
    var loggedIn =0
    
    var position = 0
    lateinit var courseList : Course
    lateinit var allCourseList: Course
}
