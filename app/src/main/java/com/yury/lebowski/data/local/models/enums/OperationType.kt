package com.yury.lebowski.data.local.models.enums

enum class OperationType constructor(
        val effect: Int
){
    Income(1),
    Expenditure(-1);

    companion object {
        fun findByEffect(value : Int): OperationType? {
            for (item in OperationType.values())
                if (item.effect == value)
                    return item
            return null
        }
    }
}