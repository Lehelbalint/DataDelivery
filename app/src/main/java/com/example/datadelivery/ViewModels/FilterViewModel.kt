package com.example.datadelivery.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.Course
import com.example.datadelivery.Department_G
import com.example.datadelivery.Models.DepartmentClass
import kotlinx.coroutines.launch
import java.lang.Exception

class FilterViewModelFactory(
    private val repository: DateDeliveryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilterViewModel( repository) as T
    }
}

class FilterViewModel(val repository: DateDeliveryRepository) : ViewModel() {
    var courseList = MutableLiveData<Course>()
    var departmentList = MutableLiveData<DepartmentClass>()

    fun getCourses() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = repository.getCourses()
                    if (response.isSuccessful) {
                        // userList.value = response.body()?.data
                        courseList.value = response.body()
                        Log.i("xxx-res", courseList.value.toString())
                    } else {
                        Log.i("xxx-muvm", response.message())
                    }
                } catch (e: Exception) {
                    Log.i("xxx-err", e.toString())
                }
            }
        }
    }
    fun getDepartments() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = repository.getDepartments()
                    if (response.isSuccessful) {
                        // userList.value = response.body()?.data
                        departmentList.value = response.body()
                        Log.i("xxx-res", departmentList.value.toString())
                    } else {
                        Log.i("xxx-muvm", response.message())
                    }
                } catch (e: Exception) {
                    Log.i("xxx-err", e.toString())
                }
            }
        }
    }
}