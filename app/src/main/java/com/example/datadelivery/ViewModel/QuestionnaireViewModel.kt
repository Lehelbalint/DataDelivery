package com.example.datadelivery.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.Model.AddRatingRequest
import com.example.datadelivery.Model.AnswerMessage
import kotlinx.coroutines.launch
import java.lang.Exception

class QuestionnaireViewModelFactory(
    private val repository: DateDeliveryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionnaireViewModel( repository) as T
    }
}

class QuestionnaireViewModel(val repository: DateDeliveryRepository) : ViewModel() {
    var message = MutableLiveData<AnswerMessage>()

    fun addRating(request : AddRatingRequest) {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response = repository.addRating(request)
                    if (response.isSuccessful) {
                        // userList.value = response.body()?.data
                       message.value = response.body()
                        Log.i("xxx-res",message.value.toString())
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