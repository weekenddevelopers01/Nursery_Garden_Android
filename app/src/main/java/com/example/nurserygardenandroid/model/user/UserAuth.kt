package com.example.nurserygardenandroid.model.user

import android.os.Parcel
import android.os.Parcelable

data class UserAuth(
    val auth: Auth?,
    val token: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Auth::class.java.classLoader),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(auth, flags)
        parcel.writeString(token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserAuth> {
        override fun createFromParcel(parcel: Parcel): UserAuth {
            return UserAuth(parcel)
        }

        override fun newArray(size: Int): Array<UserAuth?> {
            return arrayOfNulls(size)
        }
    }
}