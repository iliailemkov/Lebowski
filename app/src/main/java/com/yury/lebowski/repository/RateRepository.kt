package com.yury.lebowski.repository

import com.yury.lebowski.models.CurrencyType

fun CurrencyType.convertFromUniversal(): Double {
    return when (this) {
        CurrencyType.Dollar -> 1.0
        CurrencyType.Ruble -> 63.45
    }
}