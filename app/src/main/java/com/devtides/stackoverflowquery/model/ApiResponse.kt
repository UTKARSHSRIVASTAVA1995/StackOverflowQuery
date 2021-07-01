package com.devtides.stackoverflowquery.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import com.google.gson.annotations.SerializedName
import java.util.*

data class ResponseWrapper<T>(
    val items: List<T>
)

data class Questions(
    @SerializedName("question_id")
    val quseions: Int?,

    val title: String?,
    val score: String?,

    @SerializedName("creation_date")
    val date: Long?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(quseions)
        parcel.writeString(title)
        parcel.writeString(score)
        parcel.writeValue(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Questions> {
        override fun createFromParcel(parcel: Parcel): Questions {
            return Questions(parcel)
        }

        override fun newArray(size: Int): Array<Questions?> {
            return arrayOfNulls(size)
        }
    }
}

data class Answers(
    @SerializedName("answer_id")
    val answerId: Int?,

    @SerializedName("is_accepted")
    val isAccepted: Boolean?,

    val score: String?,

    @SerializedName("creation_date")
    val date: Long?
) {
    override fun toString() =
        "$answerId -$score - ${
            getDate(date)
        } - ${if (isAccepted == true) "ACCEPTED" else "NOT ACCEPTED"}"
}

fun convertTitle(title: String?) =

    if (Build.VERSION.SDK_INT >= 24)
        Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
    else
        Html.fromHtml(title).toString()

fun getDate(timeStamp: Long?): String {
    var time = ""
    timeStamp?.let {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp * 1000
        time = android.text.format.DateFormat.format("dd-MM-yyyy  hh:mm:ss", cal).toString()
    }
    return time

}

