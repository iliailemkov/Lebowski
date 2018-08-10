package com.yury.lebowski.data.local.converters

import com.yury.lebowski.data.local.models.enums.OperationType
import org.junit.Assert
import org.junit.Test

class OperationTypeConverterTest {

    val operationTypeConverter = OperationTypeConverter()

    @Test
    fun testOperationTypeToEffect() {
        Assert.assertEquals(operationTypeConverter.effectToInt(OperationType.Income), 1)
    }

    @Test
    fun testEffectToOperationType() {
        Assert.assertEquals(operationTypeConverter.fromInt(-1), OperationType.Expenditure)
    }
}