package com.yury.lebowski.models

import com.yury.lebowski.R

data class Account(
        val nameResourceId: Int,
        var currentBalanceInUniversal: Double
)

val Accounts = listOf<Account>(
        Account(R.string.cash, 16.0),
        Account(R.string.credit_card, 1050.0)
)