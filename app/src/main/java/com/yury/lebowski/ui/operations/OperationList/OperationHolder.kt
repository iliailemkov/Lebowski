package com.yury.lebowski.ui.operations.OperationList

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.enums.OperationState
import kotlinx.android.synthetic.main.operation_list_item.view.*

class OperationHolder(view: View, operationState: OperationState) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    val amount = view.tv_amount!!
    val date = view.tv_date!!
    val group =  view.tv_name
    val operationState = operationState

    init {
        view.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        if (operationState == OperationState.Normal) {
            menu.add(adapterPosition, v.id, 0, R.string.context_menu_to_draft)
            menu.add(adapterPosition, v.id, 1, R.string.context_menu_delete)
        } else {
            menu.add(adapterPosition, v.id, 0, R.string.context_menu_add_operation)
            menu.add(adapterPosition, v.id, 1, R.string.context_menu_delete)
        }
    }
}