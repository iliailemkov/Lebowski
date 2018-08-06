package com.yury.lebowski.data.local.converters

import androidx.room.TypeConverter
import com.yury.lebowski.data.local.models.enums.OperationType

class OperationTypeConverter {

    @TypeConverter
    fun fromInt(value: Int?): OperationType? {
        return if (value == null) null else OperationType.findByEffect(value)
    }

    @TypeConverter
    fun effectToInt(operationType: OperationType?): Int? {
        return if (operationType == null) {
            null
        } else {
            operationType.effect
        }
    }
}