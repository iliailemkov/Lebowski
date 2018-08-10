package com.yury.lebowski.data.local.models.enums

enum class OperationState {
    Normal,
    Draft,
    Periodical;

    companion object {
        fun findByOrdinal(value : Int): OperationState? {
            for (item in OperationState.values())
                if (item.ordinal == value)
                    return item
            return null
        }
    }
}