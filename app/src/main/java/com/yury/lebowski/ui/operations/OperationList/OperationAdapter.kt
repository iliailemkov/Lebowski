package com.yury.lebowski.ui.operations.OperationList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Operation
import java.text.SimpleDateFormat

class OperationAdapter : RecyclerView.Adapter<OperationHolder>() {

    var operations: List<Operation> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): OperationHolder {
        return OperationHolder(LayoutInflater.from(parent.context).inflate(R.layout.operation_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return operations.size
    }

    override fun onBindViewHolder(holder: OperationHolder, pos: Int) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

        if (operations[pos].amount >= 0) {
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark))
            holder.amount.setText("+" + String.format("%.2f", operations[pos].amount))
        } else {
            holder.amount.text = String.format("%.2f", operations[pos].amount)
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark))
        }
        holder.date.text = dateFormat.format(operations[pos].date)
    }

}