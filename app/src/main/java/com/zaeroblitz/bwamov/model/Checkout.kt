package com.zaeroblitz.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Checkout (
    var kursi: String? = "",
    var harga: Int? = 0
) : Parcelable