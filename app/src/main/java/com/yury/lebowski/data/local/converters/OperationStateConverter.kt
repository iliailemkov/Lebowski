package com.yury.lebowski.data.local.converters

import androidx.room.TypeConverter
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationState


class OperationStateConverter {

    @TypeConverter
    fun fromOrdinal(value: Int?): OperationState? {
        return if (value == null) null else OperationState.findByOrdinal(value)
    }

    @TypeConverter
    fun toOrdinal(operationState: OperationState?): Int? {
        return if (operationState == null) {
            null
        } else {
            operationState.ordinal
        }
    }
}