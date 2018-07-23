package com.yury.lebowski.service

import com.yury.lebowski.models.Effect
import com.yury.lebowski.models.Record
import com.yury.lebowski.repository.convertFromUniversal

fun calculateBalanceAfterOperations(start_balance: Double, operations: List<Record>): Double {
    return start_balance + operations.sumByDouble { it.operationType.Effect() * it.amount / it.currencyType.convertFromUniversal() }
}