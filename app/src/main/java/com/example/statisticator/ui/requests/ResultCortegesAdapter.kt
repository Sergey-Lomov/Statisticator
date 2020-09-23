package com.example.statisticator.ui.requests

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R
import com.example.statisticator.models.AttributesContainer
import com.example.statisticator.models.schema.QueryCortegesNode

interface CortegesListDelegate {
    fun cortegeSelect(cortege: AttributesContainer)
}

class ResultCortegesAdapter(private val node: QueryCortegesNode,
                            private val context: Context,
                            var delegate: CortegesListDelegate? = null
): RecyclerView.Adapter<ResultCortegesAdapter.ViewHolder>() {
    private val corteges = node.content.toList()

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val recyclerView: RecyclerView = v.findViewById(R.id.recyclerView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.result_corteges_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cortege = corteges[position]
        viewHolder.recyclerView.adapter = ResultCortegeAdapter(cortege, context)
        viewHolder.recyclerView.layoutManager = LinearLayoutManager(context)

        viewHolder.itemView.setOnClickListener {
            delegate?.cortegeSelect(cortege)
        }

        val backColor = if (position %2 == 0) R.color.evenCortege else R.color.oddCortage
        val colorId = context.resources.getColor(backColor)
        viewHolder.itemView.setBackgroundColor(colorId)
    }

    override fun getItemCount() = corteges.size
}
