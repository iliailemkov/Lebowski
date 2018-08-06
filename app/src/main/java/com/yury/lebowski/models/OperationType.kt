package com.yury.lebowski.models

enum class OperationType {
    Income {
        override fun effect(): Double {
            return 1.0
        }
    },
    Expenditure {
        override fun effect(): Double {
            return -1.0
        }
    };

    abstract fun effect(): Double;
}