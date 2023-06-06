package com.example.datadelivery

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.datadelivery.API.DateDeliveryRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class StudentViewModelFactory(
    private val repository: DateDeliveryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudentViewModel( repository) as T
    }
}

class StudentViewModel(val repository: DateDeliveryRepository) : ViewModel() {
    init {
        //getStudents();
    }

    var userList = MutableLiveData<StudentData>()
    //var message = MutableLiveData <AnswerMessage>()

    fun getStudents() {
        viewModelScope.launch {
            try {
                val response = repository.getStudents()
                if(response.isSuccessful) {
                  // userList.value = response.body()?.data
                    userList.value = response.body()
                   // Log.i("xxx-res", userList.value.toString())
                } else{
                    Log.i("xxx-muvm", response.message())
                }
            } catch (e: Exception) {
                Log.i("xx", e.toString())
            }
        }
    }

}