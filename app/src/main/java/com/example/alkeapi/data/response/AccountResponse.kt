package com.example.alkeapi.data.response

import com.example.alkeapi.data.model.User

data class AccountResponse(
    val previousPage: String?,
    val nextPage: String?,
    val data: MutableList<AccountDataResponse>
) {

}