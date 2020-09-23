package com.example.statisticator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.MenuItemModel
import com.example.statisticator.models.schema.MenuModel
import com.example.statisticator.models.schema.MenuStyle
import com.example.statisticator.service.SchemasManager
import com.example.statisticator.ui.uihelpers.GridSpacingItemDecoration

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
            val iconsFolder = SchemasManager(requireContext()).iconFolder()
            when (model.style) {
                MenuStyle.Full -> setupFullLayout()
                MenuStyle.Short -> setupShortLayout()
            }
            recyclerView.adapter = MenuAdapter(model, iconsFolder, delegate)
        }
    }

    private fun setupFullLayout() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupShortLayout() {
        val spacing = resources.getDimension(R.dimen.list_items_space).toInt()
        val decoration = GridSpacingItemDecoration(Constants.MENU_ITEMS_PER_ROW, spacing, true)
        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = GridLayoutManager(activity, Constants.MENU_ITEMS_PER_ROW)
    }
}