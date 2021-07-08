package com.riquest.sistemmanajemenmenu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Invoice(
        var MENU: ArrayList<InvoiceMenu>? = ArrayList(),
        var NAMA: String? = "",
        var NOMEJA: String? = "0",
        var TANGGAL: String? = "",
        var TOTAL: Int? = 0
) : Parcelable
