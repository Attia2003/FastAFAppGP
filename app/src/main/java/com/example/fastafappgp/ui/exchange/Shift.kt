package com.example.fastafappgp.ui.exchange

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shift(
    val id: Int?,
    val startTime: String?,
    val endTime: String?,
    val status: String?,
    val createdAt: String?,
    val updatedAt: String?
) : Parcelable 