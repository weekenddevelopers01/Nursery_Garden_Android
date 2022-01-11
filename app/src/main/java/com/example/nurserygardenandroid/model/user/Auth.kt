package com.example.nurserygardenandroid.model.user

import android.os.Parcel
import android.os.Parcelable

data class Auth(
    val __v: Int,
    val _id: String?,
    val email: String?,
    val isProfiled: Boolean
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(__v)
        parcel.writeString(_id)
        parcel.writeString(email)
        parcel.writeByte(if (isProfiled) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Auth> {
        override fun createFromParcel(parcel: Parcel): Auth {
            return Auth(parcel)
        }

        override fun newArray(size: Int): Array<Auth?> {
            return arrayOfNulls(size)
        }
    }
}