package com.riquest.sistemmanajemenmenu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InvoiceMenu(
        var HARGA: Int? = 0,
        var NAMA: String? = "",
        var QTY: String = "0",
        var SUBTOTAL: String = "0",
) : Parcelable
