package com.yury.lebowski.util.data_binding.spinner

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.InverseBindingListener

object SpinnerExtensions {

    /**
     * set spinner entries
     */
    fun Spinner.setSpinnerEntries(entries: List<Any>?) {
        if (entries != null) {
            val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, entries)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = arrayAdapter
        }
    }

    /**
     * set spinner onItemSelectedListener listener
     */
    fun Spinner.setSpinnerItemSelectedListener(listener: ItemSelectedListener?) {
        onItemSelectedListener = if (listener == null) {
            null
        } else {
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    if (tag != position) {
                        listener.onItemSelected(parent.getItemAtPosition(position))
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    /**
     * set spinner onItemSelectedListener listener
     */
    fun Spinner.setSpinnerInverseBindingListener(listener: InverseBindingListener?) {
        if (listener == null) {
            onItemSelectedListener = null
        } else {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    if (tag != position) {
                        listener.onChange()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    /**
     * set spinner value
     */
    fun Spinner.setSpinnerValue(value: Any?) {
        if (adapter != null) {
            @Suppress("UNCHECKED_CAST")
            val position = (adapter as ArrayAdapter<Any>).getPosition(value)
            setSelection(position, false)
            tag = position
        }
    }

    /**
     * get spinner value
     */
    fun Spinner.getSpinnerValue(): Any? {
        return selectedItem
    }

    interface ItemSelectedListener {
        fun onItemSelected(item: Any)
    }
}