package com.yury.lebowski.service

import com.yury.lebowski.models.Record
import com.yury.lebowski.repository.RateRepository


fun calculateBalanceAfterOperations(start_balance: Double, operations: List<Record>): Double {
    val rateRepository = RateRepository()
    return start_balance + operations.sumByDouble { it.operationType.effect() * it.amount / rateRepository.getLastOfflineRate(it.currencyType) }
}