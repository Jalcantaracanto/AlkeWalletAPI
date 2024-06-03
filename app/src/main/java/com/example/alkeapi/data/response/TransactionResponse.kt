package com.example.alkeapi.data.response

data class TransactionResponse(
    val data: MutableList<TransactionDataResponse>,
    val nextPage: String?,
    val previousPage: String?
)