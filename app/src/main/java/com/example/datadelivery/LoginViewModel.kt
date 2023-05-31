package com.example.datadelivery


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.datadelivery.API.DateDeliveryRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModelFactory(
    private val repository: DateDeliveryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel( repository) as T
    }
}

class LoginViewModel(val repository: DateDeliveryRepository) : ViewModel() {
    var userList = MutableLiveData<StudentData>()

    fun getStudents() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = repository.getStudents()
                    if (response.isSuccessful) {
                        // userList.value = response.body()?.data
                        userList.value = response.body()
                        Log.i("xxx-res", userList.value.toString())
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

