package com.yury.lebowski.models

enum class OperationType {
    Income, Expenditure
}

fun OperationType.Effect() : Double {
    return when(this) {
        OperationType.Income -> 1.0
        OperationType.Expenditure -> -1.0
    }
}