package com.yury.lebowski.data.models

enum class OperationType {
    Income {
        override fun effect() = 1.0
    },
    Expenditure {
        override fun effect() = -1.0
    };

    abstract fun effect(): Double;
}