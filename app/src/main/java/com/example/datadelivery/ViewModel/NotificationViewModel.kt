package com.example.datadelivery

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.Model.Rating
import kotlinx.coroutines.launch
import java.lang.Exception


class NotificationViewModelFactory(
    private val repository: DateDeliveryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationViewModel( repository) as T
    }
}
class NotificationViewModel(private val repository: DateDeliveryRepository) : ViewModel() {
    var gradeList = MutableLiveData<Grade>()
    var ratings = MutableLiveData<Rating>()

    fun getGrades() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = repository.getGrades()
                    if (response.isSuccessful) {
                        // userList.value = response.body()?.data
                        gradeList.value = response.body()
                        Log.i("xxx-res", gradeList.value.toString())
                    } else {
                        Log.i("xxx-muvm", response.message())
                    }
                } catch (e: Exception) {
                    Log.i("xxx-err", e.toString())
                }
            }
        }
    }
    fun getRatings() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = repository.getRatings()
                    if (response.isSuccessful) {
                        // userList.value = response.body()?.data
                        ratings.value = response.body()
                        Log.i("xxx-res", ratings.value.toString())
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
