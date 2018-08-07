package com.yury.lebowski.data.local.models.enums

enum class CurrencyType(
        val code: String,
        val number: Int
) {
    Ruble("RUB", 643),
    Dollar("USD", 840);

    companion object {
        fun findByCode(value: String): CurrencyType? {
            for (item in CurrencyType.values())
                if (item.code == value)
                    return item
            return null
        }
    }
}