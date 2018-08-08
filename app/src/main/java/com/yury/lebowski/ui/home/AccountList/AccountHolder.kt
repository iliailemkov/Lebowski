package com.yury.lebowski.ui.home.AccountList

import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yury.lebowski.R
import kotlinx.android.synthetic.main.account_list_item.view.*
import android.widget.Toast



class AccountHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    val amount = view.balance_main!!
    val currency = view.currency
    val name = view.balance_name!!

    init {
        view.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu.add(adapterPosition, v.id, 0, R.string.context_menu_delete)
    }
}