package com.btavares.coins.app.data.sharepreference

import android.content.Context
import com.btavares.library_base.presentation.extension.USER_ID
import com.btavares.library_base.presentation.extension.USER_INFO

class UserSharePreferences(private val context: Context) {

    fun saveUserId(id : Long){
        val sharePreferences = this.context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        val editor = sharePreferences.edit()
        editor.putLong(USER_ID, id)
        editor.apply()
    }

    fun getUserId() : Long {
        val sharePreferences = this.context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        return sharePreferences.getLong(USER_ID, 0)
    }

    fun checkIfUserInfoExists() = this.context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).contains(
        USER_ID)
}