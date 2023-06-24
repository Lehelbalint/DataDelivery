package com.example.datadelivery.ViewModel

import androidx.lifecycle.ViewModel
import com.example.datadelivery.Data_G

class SharedChartsViewModel : ViewModel(){
    lateinit var GradesHistogram : List<Data_G>
    lateinit var GradeHistogram2: List<Data_G>
    lateinit var course: String
    lateinit var course2: String
    lateinit var department: String
    lateinit var department2: String
    lateinit var year: String
    lateinit var year2: String
    lateinit var type: String
    lateinit var type2: String

}