package com.yury.lebowski.service

import com.yury.lebowski.data.models.Operation
import com.yury.lebowski.data.repository.RateRepository


/*fun calculateBalanceAfterOperations(start_balance: Double, operations: List<Operation>): Double {
    val rateRepository = RateRepository()
    return start_balance + operations.sumByDouble { it.operationType.effect * it.amount / rateRepository.getLastOfflineRate(it.currencyType) }
}*/