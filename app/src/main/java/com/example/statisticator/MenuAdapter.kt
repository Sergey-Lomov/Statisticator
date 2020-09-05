package com.example.statisticator

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.models.MenuModel

class MenuAdapter(private var model: MenuModel, private val delegate: MenuFragmentDelegate?) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView
        val icon: ImageView

        init {
            title = v.findViewById(R.id.title)
            icon = v.findViewById(R.id.icon)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.full_menu_item, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = model.items[position].title
        viewHolder.itemView.setOnClickListener {
            val item = model.items[position]
            delegate?.itemClick(item)
        }
    }

    override fun getItemCount() = model.items.size

    fun updateModel(model: MenuModel) {
        this.model = model
        notifyDataSetChanged()
    }
}