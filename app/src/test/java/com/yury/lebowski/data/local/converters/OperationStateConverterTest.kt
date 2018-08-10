package com.yury.lebowski.data.local.converters

import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.local.models.enums.OperationType
import org.junit.Assert
import org.junit.Test

class OperationStateConverterTest {

    val operationStateConverter = OperationStateConverter()

    @Test
    fun testOperationStateToOrdinal() {
        Assert.assertEquals(operationStateConverter.fromOrdinal(0), OperationState.Normal)
    }

    @Test
    fun testEffectToOperationType() {
        Assert.assertEquals(operationStateConverter.toOrdinal(OperationState.Normal), 0)
    }
}