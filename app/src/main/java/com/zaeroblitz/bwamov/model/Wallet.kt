package com.zaeroblitz.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wallet (
    var title: String? = "",
    var date: String? = "",
    var balance: Double? = 0.0,
    var status: String? = ""
) : Parcelable