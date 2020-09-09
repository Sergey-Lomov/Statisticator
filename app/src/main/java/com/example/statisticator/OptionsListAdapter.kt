package com.example.statisticator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.models.MenuModel

interface OptionsListDelegate {
    fun optionAtIndexClick(index: Int)
}

class OptionsListAdapter(private val options: Array<String>, var delegate: OptionsListDelegate? = null) :
    RecyclerView.Adapter<OptionsListAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView

        init {
            title = v.findViewById(R.id.title)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.options_list_item, viewGroup, false)

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