package com.example.statisticator.ui.requests

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R
import com.example.statisticator.models.AttributesContainer

class ResultCortegeAdapter(private var cortege: AttributesContainer,
                           private val context: Context
): RecyclerView.Adapter<ResultCortegeAdapter.ViewHolder>() {
    private val attributes = cortege.content.keys.toList()

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title)
        val value: TextView = v.findViewById(R.id.value)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.cortege_attribute_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = attributes[position]
        val value = cortege.content[attributes[position]]
        viewHolder.value.text = (value ?: "").toString()
    }

    override fun getItemCount() = attributes.size
}
