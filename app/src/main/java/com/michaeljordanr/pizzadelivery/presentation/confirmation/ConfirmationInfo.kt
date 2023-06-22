package com.michaeljordanr.pizzadelivery.presentation.confirmation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfirmationInfo(
    val message: String,
    val total: Double
): Parcelable
