package com.yury.lebowski.data.local.db

import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.CurrencyType
import com.yury.lebowski.data.local.models.OperationType

object PrepopulateData {
    val accounts = listOf(
            Account(1, R.string.app_name, 0.0, CurrencyType.Dollar),
            Account(2, R.string.name, 0.0, CurrencyType.Ruble)
    )

    val categories = listOf(
            Category(1, OperationType.Expenditure, R.string.vehicle),
            Category(2, (OperationType.Expenditure), R.string.housing),
            Category(3, (OperationType.Expenditure), R.string.health_and_beaty),
            Category(4, (OperationType.Expenditure), R.string.art),
            Category(5, (OperationType.Expenditure), R.string.commission),
            Category(6, (OperationType.Expenditure), R.string.utility_payments),
            Category(7, (OperationType.Expenditure), R.string.communication),
            Category(8, (OperationType.Expenditure), R.string.education),
            Category(9, (OperationType.Expenditure), R.string.entertainment_and_leisure),
            Category(10, (OperationType.Expenditure), R.string.products),
            Category(11, (OperationType.Expenditure), R.string.shops),
            Category(12, (OperationType.Expenditure), R.string.other_expenses),
            Category(13, (OperationType.Income), R.string.refund),
            Category(14, (OperationType.Income), R.string.salary),
            Category(15, (OperationType.Income), R.string.other_income)
    )
}