package com.example.statisticator.ui.attributes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.ColorsListAttribute
import com.example.statisticator.ui.uihelpers.GridSpacingItemDecoration

class ColorsListFragment: Fragment(), OptionsListDelegate {

    private var delegate: ValueEditorDelegate? = null
    private lateinit var attribute: ColorsListAttribute

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        delegate = arguments?.get(Constants.DELEGATE_BUNDLE_KEY) as ValueEditorDelegate?
        attribute = arguments?.get(Constants.ATTRIBUTE_BUNDLE_KEY) as ColorsListAttribute? ?:
                throw Exception("Create number interval fragment with no valid attribute in arguments")

        val rootView = inflater.inflate(R.layout.list_selector_fragment, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, Constants.COLORS_LIST_ITEMS_PER_ROW)
        val spacing = resources.getDimension(R.dimen.list_items_space).toInt()
        val decoration = GridSpacingItemDecoration(Constants.COLORS_LIST_ITEMS_PER_ROW, spacing, false)
        recyclerView.addItemDecoration(decoration)
        recyclerView.adapter = ColorsListAdapter(attribute.colors, this)

        return rootView
    }

    override fun optionAtIndexClick(index: Int) {
        delegate?.valueDidChanged(attribute.colors[index])
    }

    companion object {
        fun newInstance(attribute: ColorsListAttribute,
                        delegate: ValueEditorDelegate? = null) = ColorsListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constants.ATTRIBUTE_BUNDLE_KEY, attribute)
                putSerializable(Constants.DELEGATE_BUNDLE_KEY, delegate)
            }
        }
    }
}