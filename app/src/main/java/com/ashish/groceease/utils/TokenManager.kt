package com.ashish.groceease.utils

import android.content.Context
import com.ashish.groceease.utils.Constant.PREF_TOKEN_FILE
import com.ashish.groceease.utils.Constant.USER_ID
import com.ashish.groceease.utils.Constant.USER_NAME
import com.ashish.groceease.utils.Constant.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val pref=context.getSharedPreferences(PREF_TOKEN_FILE,Context.MODE_PRIVATE)

    fun saveToken(token:String){
        val editor=pref.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }

    fun saveName(name:String){
        val editor=pref.edit()
        editor.putString(USER_NAME,name)
        editor.apply()
    }

    fun saveUserId(id:String){
        val editor=pref.edit()
        editor.putString(USER_ID,id)
        editor.apply()
    }

    fun getToken():String?{
        return pref.getString(USER_TOKEN,null)
    }
    fun getUserId():String?{
        return pref.getString(USER_ID,null)
    }
    fun getUserName():String?{
        return pref.getString(USER_NAME,null)
    }

    fun removeToken(){
        val editor=pref.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }

    fun removeUserId(){
        val editor=pref.edit()
        editor.remove(USER_ID)
        editor.apply()
    }
    fun removeUserName(){
        val editor=pref.edit()
        editor.remove(USER_NAME)
        editor.apply()
    }
}