package cn.wsgwz.baselibrary.retrofit.bean


import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

class RequestData(val id: String, val uri:Uri,val method:String) :Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Uri::class.java.classLoader),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(uri, flags)
        parcel.writeString(method)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RequestData> {
        override fun createFromParcel(parcel: Parcel): RequestData {
            return RequestData(parcel)
        }

        override fun newArray(size: Int): Array<RequestData?> {
            return arrayOfNulls(size)
        }
    }

}