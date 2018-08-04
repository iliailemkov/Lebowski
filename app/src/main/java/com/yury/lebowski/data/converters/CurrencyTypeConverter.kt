package com.yury.lebowski.data.converters

import androidx.room.TypeConverter
import com.yury.lebowski.data.models.CurrencyType
import com.yury.lebowski.data.models.OperationType


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