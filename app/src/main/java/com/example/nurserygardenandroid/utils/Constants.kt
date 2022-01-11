package com.example.nurserygardenandroid.utils

import com.example.nurserygardenandroid.model.user.UserProfile

class Constants {
    companion object {
        const val AUTH_TOKEN:String = "AUTH_TOKEN_FROM_SERVER"
        const val TOKEN:String = "AUTH_TOKEN"
        const val USERNAME = "USERNAME"
        const val EMAIL = "EMAIL"
        const val MOBILE = "MOBILE"
        const val ISPROFILED:String = "ISPROFILED"
        const val HEADER_AUTHORIZATION = "Authorization"
        const val QUERY_AID="aid"
        const val BEARER = "Bearer "
        const val PARCELABLE_USER:String = "parcelable_user_after_login"

        //User profile Columns in server DB
        const val USERPROFILE_FNAME ="f_name"
        const val USERPROFILE_LNAME ="l_name"
        const val USERPROFILE_EMAIL ="email"
        const val USERPROFILE_ISVERIFIED ="isVerified"
        const val USERPROFILE_PHONE ="phone"
    }

}