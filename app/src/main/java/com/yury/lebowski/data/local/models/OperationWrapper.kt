package com.yury.lebowski.data.local.models

import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.local.models.enums.OperationType
import java.util.*

data class OperationWrapper(
        val id: Long?,
        val date: Date,
        val operationType: OperationType,
        val operationState: OperationState,
        val amount: Double,
        val accountId: Long,
        val categoryId: Long,
        val categoryName: String
)