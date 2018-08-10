package com.yury.lebowski.data.local.db

import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.ExchangeRate
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationType
import java.util.*


object PrepopulateData {
    val categories = listOf(
            Category(1, OperationType.Expenditure, LebowskiApplication.instance.getString(R.string.vehicle)),
            Category(2, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.housing)),
            Category(3, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.health_and_beaty)),
            Category(4, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.art)),
            Category(5, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.commission)),
            Category(6, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.utility_payments)),
            Category(7, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.communication)),
            Category(8, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.education)),
            Category(9, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.entertainment_and_leisure)),
            Category(10, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.products)),
            Category(11, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.shops)),
            Category(12, (OperationType.Expenditure), LebowskiApplication.instance.getString(R.string.other_expenses)),
            Category(13, (OperationType.Income), LebowskiApplication.instance.getString(R.string.refund)),
            Category(14, (OperationType.Income), LebowskiApplication.instance.getString(R.string.salary)),
            Category(15, (OperationType.Income), LebowskiApplication.instance.getString(R.string.other_income))
    )

    val rates = listOf(
            ExchangeRate(1, CurrencyType.Dollar, CurrencyType.Ruble, 0.01, Date()),
            ExchangeRate(2, CurrencyType.Ruble, CurrencyType.Dollar, 63.0, Date())
    )
}