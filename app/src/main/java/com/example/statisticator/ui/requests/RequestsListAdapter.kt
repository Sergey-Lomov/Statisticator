package com.example.statisticator.ui.requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R
import com.example.statisticator.models.schema.DataRequest

interface RequestsListDelegate {
    fun itemClick(item: DataRequest)
}

class RequestsListAdapter(private var requests: ArrayList<DataRequest>,
                          var delegate: RequestsListDelegate? = null
): RecyclerView.Adapter<RequestsListAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.request_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = requests[position].title
        viewHolder.itemView.setOnClickListener {
            val item = requests[position]
            delegate?.itemClick(item)
        }
    }

    override fun getItemCount() = requests.size
}