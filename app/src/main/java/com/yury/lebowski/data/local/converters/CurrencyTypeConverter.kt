package com.yury.lebowski.data.local.converters

import androidx.room.TypeConverter
import com.yury.lebowski.data.local.models.CurrencyType
import com.yury.lebowski.data.local.models.OperationType


class CurrencyTypeConverter {

    @TypeConverter
    fun fromString(value: String?): CurrencyType? {
        return if (value == null) null else CurrencyType.findByCode(value)
    }

    @TypeConverter
    fun codeToString(operationType: CurrencyType?): String? {
        return if (operationType == null) {
            null
        } else {
            operationType.code
        }
    }
}