package com.example.datadelivery.Model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnswerMessage(
    var message: String
)