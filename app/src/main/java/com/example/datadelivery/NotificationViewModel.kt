package com.example.datadelivery

import android.app.Notification
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.datadelivery.API.DateDeliveryRepository
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
}
