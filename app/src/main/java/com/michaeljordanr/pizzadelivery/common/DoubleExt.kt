package com.michaeljordanr.pizzadelivery.common

import java.text.DecimalFormat

fun Double.currency(): String {
    val format = DecimalFormat("$#,###.00")
    format.isDecimalSeparatorAlwaysShown = false
    return if (this > 0) {
        format.format(this).toString()
    } else {
        "$0.00"
    }
}