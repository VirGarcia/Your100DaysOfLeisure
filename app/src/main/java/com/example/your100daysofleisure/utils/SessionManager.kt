package com.example.your100daysofleisure.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.your100daysofleisure.adapters.LeisureViewHolder


class SessionManager (context: Context) {
    companion object {
        const val USER_ZIPCODE = "USER_ZIPCODE"
        const val INFO_LEISURE = "INFO_LEISURE"
        const val USER_NAME = "USER_NAME"
        const val FAVORITE_LEISURE = "FAVORITE_LEISURE"
    }
    private val sharedPref: SharedPreferences

    init {
        sharedPref = context.getSharedPreferences("leisure_session", Context.MODE_PRIVATE)
    }

    fun setUserName(name: String) {
        val userName = sharedPref.edit()
        userName.putString(USER_NAME, name)
        userName.apply()
    }

    fun getUserName() : String? {
        return sharedPref.getString(USER_NAME, null)
    }

    fun setUserZipCode(name: String) {
        val userName = sharedPref.edit()
        userName.putString(USER_ZIPCODE, name)
        userName.apply()
    }

    fun getUserZipCode() : String? {
        return sharedPref.getString(USER_ZIPCODE, null)
    }


    fun setInfoLeisure(id: String) {
        val editor = sharedPref.edit()
        editor.putString(INFO_LEISURE, id)
        editor.apply()
    }

    fun getInfoLeisure() : String? {
        return sharedPref.getString(INFO_LEISURE, null)
    }

    fun isFavorite(leisureId: String) : Boolean {
        return getFavoriteLeisure()?.equals(leisureId) ?: false
    }
    fun setFavoriteLeisure(id: String) {
        val editor = sharedPref.edit()
        editor.putString(FAVORITE_LEISURE, id)
        editor.apply()
    }
    fun getFavoriteLeisure() : String? {
        return sharedPref.getString(FAVORITE_LEISURE, null)
    }
}