package com.example.datadelivery.ViewModels

import androidx.lifecycle.ViewModel
import com.example.datadelivery.Data_G

class SharedGradeViewModel: ViewModel() {
    lateinit var myGrades : List<Data_G>
    var position = 0
}