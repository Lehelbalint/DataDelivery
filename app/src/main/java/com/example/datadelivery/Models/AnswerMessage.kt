package com.example.datadelivery.Models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnswerMessage(
    var message: String
)