package com.example.alkeapi.application

import android.content.Context
import android.content.SharedPreferences
import com.example.alkeapi.data.response.UserDataResponse

object SharedPreferencesHelper {

    private const val PREFS_NAME = "ALKEWALLET_PREF"
    private const val TOKEN_KEY = "ALKE_TOKEN"
    private const val ID_USER_KEY = "ID_USER"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, 0)
    }

    fun saveToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(TOKEN_KEY, null)
    }

    fun clearToken(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }

    fun idUserLogged(context: Context, idUser: Int) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(ID_USER_KEY, idUser.toString())
        editor.apply()
    }

    fun getIdUser(context: Context): Int? {
        return getSharedPreferences(context).getString(ID_USER_KEY, null)?.toInt()
    }
}