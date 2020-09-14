package com.example.statisticator.ui.attributes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R

class StringsListAdapter(private val options: Array<String>,
                         override var delegate: OptionsListDelegate?
) : RecyclerView.Adapter<StringsListAdapter.ViewHolder>(), OptionsListAdapter {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.strings_list_item, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = options[position]
        viewHolder.itemView.setOnClickListener {
            delegate?.optionAtIndexClick(position)
        }
    }

    override fun getItemCount() = options.size
}