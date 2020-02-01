package com.example.taskapp.fragments.addReminder

import android.os.Parcelable
import com.example.taskapp.fragments.addReminder.DetailsType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Details(val name : String,
                   val type : DetailsType
) : Parcelable {}
