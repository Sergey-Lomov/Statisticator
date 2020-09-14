package com.example.statisticator.ui.attributes

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R

class ColorsListAdapter(private val colors: ArrayList<Int>,
                        override var delegate: OptionsListDelegate?
) : RecyclerView.Adapter<ColorsListAdapter.ViewHolder>(), OptionsListAdapter {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val colorView: View = v.findViewById(R.id.colorView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.color_list_item, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val background = viewHolder.colorView.background
        when (background) {
            is ShapeDrawable -> background.paint?.color = colors[position]
            is GradientDrawable -> background.setColor(colors[position])
            is ColorDrawable -> background.color = colors[position]
        }

        viewHolder.itemView.setOnClickListener {
            delegate?.optionAtIndexClick(position)
        }
    }

    override fun getItemCount() = colors.size
}