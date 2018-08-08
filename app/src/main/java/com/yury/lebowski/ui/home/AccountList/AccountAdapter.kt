package com.yury.lebowski.ui.home.AccountList

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Account

class AccountAdapter(
        private val onClickAction: (Long) -> Unit
) : RecyclerView.Adapter<AccountHolder>() {

    var accounts: List<Account> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AccountHolder {
        return AccountHolder(LayoutInflater.from(parent.context).inflate(R.layout.account_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    override fun onBindViewHolder(holder: AccountHolder, pos: Int) {
        if (accounts[pos].balance >= 0) {
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark))
            holder.amount.setText("+" + String.format("%.2f", accounts[pos].balance))
        } else {
            holder.amount.text = String.format("%.2f", accounts[pos].balance)
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark))
        }
        holder.currency.text = accounts[pos].currencyType.code
        holder.name.text = accounts[pos].name
        holder.itemView.setOnClickListener {
            onClickAction(accounts[pos].id!!)
        }
    }
}