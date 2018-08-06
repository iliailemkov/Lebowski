package com.yury.lebowski.ui.operations.OperationList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.operation_list_item.view.*

class OperationHolder(view: View) : RecyclerView.ViewHolder(view) {
    val amount = view.tv_amount!!
    val currency = view.tv_currency!!
    val date = view.tv_date!!
    //val group =  view.tv_group_name!!
}