package com.example.alkeapi.data.network.response

import com.example.alkeapi.data.local.model.User

data class AccountResponse(
    val previousPage: String?,
    val nextPage: String?,
    val data: MutableList<AccountDataResponse>
) {

}