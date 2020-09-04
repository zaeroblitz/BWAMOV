package com.zaeroblitz.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var username: String? = "",
    var email: String? = "",
    var nama: String? = "",
    var password: String? = "",
    var saldo: Int? = 50000,
    var url: String? = ""
) : Parcelable