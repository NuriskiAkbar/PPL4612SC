package com.riquest.sistemmanajemenmenu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
        var namamak: String? = "",
        var harga: String? = "",
        var harga2: Int? = 0,
        var tag1: String? = "",
        var tag2: String? = "",
        var poster: String? = "",
        var qty: Int? = 1
) : Parcelable
