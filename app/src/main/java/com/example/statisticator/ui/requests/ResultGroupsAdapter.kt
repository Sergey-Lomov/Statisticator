package com.example.statisticator.ui.requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R
import com.example.statisticator.models.schema.QueryGroupNode
import com.example.statisticator.models.schema.QueryResultNode
import com.example.statisticator.models.schema.QueryValueNode

interface GroupsListDelegate {
    fun resultSelect(node: QueryResultNode)
}

class ResultGroupsAdapter(private var groups: QueryGroupNode,
                          var delegate: GroupsListDelegate? = null
): RecyclerView.Adapter<ResultGroupsAdapter.ViewHolder>() {

    private var items = groups.content.keys.toList()

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title)
        val value: TextView = v.findViewById(R.id.value)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.result_group_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.title.text = item.toString()
        val value = groups.content[item] as? QueryValueNode
        viewHolder.value.text = (value?.content ?: "").toString()

        viewHolder.itemView.setOnClickListener {
            val value = groups.content[item] ?: return@setOnClickListener
            delegate?.resultSelect(value)
        }
    }

    override fun getItemCount() = items.size
}
