package com.yury.lebowski.ui.home.AccountList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.account_list_item.view.*
import kotlinx.android.synthetic.main.operation_list_item.view.*

class AccountHolder(view: View) : RecyclerView.ViewHolder(view) {
    val amount = view.balance_main!!
    val currency = view.currency
    val name =  view.balance_secondary!!
}