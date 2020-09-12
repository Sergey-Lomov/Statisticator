package com.example.statisticator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.models.schema.MenuItemModel
import com.example.statisticator.models.schema.MenuModel

interface MenuFragmentDelegate {
    fun itemClick(item: MenuItemModel)
}

class MenuFragment : Fragment() {

    var delegate: MenuFragmentDelegate?
        set(value) {
            val adapter = recyclerView.adapter as? MenuAdapter
            adapter?.delegate = value
        }
        get() = {
            val adapter = recyclerView.adapter as? MenuAdapter
            adapter?.delegate
        }()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.menu_fragment, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return rootView
    }

    fun updateModel(model: MenuModel) {
        val adapter = recyclerView.adapter as? MenuAdapter
        if (adapter != null) {
            adapter?.updateModel(model)
        } else {
            recyclerView.adapter = MenuAdapter(model, delegate)
        }
    }
}