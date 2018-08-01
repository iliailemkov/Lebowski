package com.yury.lebowski.models

import com.yury.lebowski.R

data class Category(
        val operationType: OperationType,
        val nameResourceId: Int
)

// TODO: move to db
// what todo with translations?
val Categories = listOf<Category>(
        Category((OperationType.Expenditure), R.string.vehicle),
        Category((OperationType.Expenditure), R.string.housing),
        Category((OperationType.Expenditure), R.string.health_and_beaty),
        Category((OperationType.Expenditure), R.string.art),
        Category((OperationType.Expenditure), R.string.commission),
        Category((OperationType.Expenditure), R.string.utility_payments),
        Category((OperationType.Expenditure), R.string.communication),
        Category((OperationType.Expenditure), R.string.education),
        Category((OperationType.Expenditure), R.string.entertainment_and_leisure),
        Category((OperationType.Expenditure), R.string.products),
        Category((OperationType.Expenditure), R.string.shops),
        Category((OperationType.Expenditure), R.string.other_expenses),
        Category((OperationType.Income), R.string.refund),
        Category((OperationType.Income), R.string.salary),
        Category((OperationType.Income), R.string.other_income)
)