package com.example.statisticator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.models.MenuItemModel
import com.example.statisticator.models.MenuModel

interface MenuFragmentDelegate {
    fun itemClick(item: MenuItemModel)
}

class MenuFragment : Fragment() {

    var delegate: MenuFragmentDelegate? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var model: MenuModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.menu_fragment, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MenuAdapter(model, delegate)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateModel(model: MenuModel) {
        val adapter = recyclerView.adapter as? MenuAdapter ?: return
        adapter.updateModel(model)
    }

    companion object {
        fun newInstance(menu: MenuModel): MenuFragment {
            val fragment = MenuFragment()
            fragment.model = menu
            return fragment
        }
    }
}