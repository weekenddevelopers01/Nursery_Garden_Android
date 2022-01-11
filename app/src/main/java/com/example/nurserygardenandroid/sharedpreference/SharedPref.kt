package com.example.nurserygardenandroid.sharedpreference


import android.content.Context
import com.example.nurserygardenandroid.utils.Constants

class SharedPref(val context:Context) {

    private final val PREF ="NurseryGarden_Preference"

    fun setAuthToken(token: String?){
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        var editor = sp.edit()
        editor.putString(Constants.TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return sp.getString(Constants.TOKEN,null)
    }

    fun setIsProfiled(isProfiled:Boolean){
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        var editor = sp.edit()
        editor.putBoolean(Constants.ISPROFILED, isProfiled)
        editor.apply()
    }

    fun getIsProfiled():Boolean{
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return sp.getBoolean(Constants.ISPROFILED, false)
    }

    fun setUserName(name:String){
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        var editor = sp.edit()
        editor.putString(Constants.USERNAME, name)
        editor.apply()
    }

    fun getUserName(): String? {
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return sp.getString(Constants.USERNAME,"")
    }

    fun setMobile(mobile:String){
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        var editor = sp.edit()
        editor.putString(Constants.MOBILE, mobile)
        editor.apply()
    }

    fun getMobile():String?{
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return  sp.getString(Constants.MOBILE, "")
    }

    fun setEmail(email:String){
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        var editor = sp.edit()
        editor.putString(Constants.EMAIL, email)
        editor.apply()
    }

    fun getEmail():String?{
        var sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return  sp.getString(Constants.EMAIL, "")
    }

    public fun setUserDeails( name:String, mobile:String, email:String){
        SharedPref(context).setUserName(name)
        SharedPref(context).setEmail(email)
        SharedPref(context).setMobile(mobile)

    }

}