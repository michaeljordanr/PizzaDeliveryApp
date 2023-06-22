package com.michaeljordanr.pizzadelivery.common

import java.text.DecimalFormat

fun Double.currency(): String {
    val format = DecimalFormat("$#,###.00")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}