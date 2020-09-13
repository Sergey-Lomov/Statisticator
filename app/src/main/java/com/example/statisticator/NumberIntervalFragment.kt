package com.example.statisticator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.NumberIntervalAttribute
import com.example.statisticator.uihelpers.GridSpacingItemDecoration

class NumberIntervalFragment : Fragment(), OptionsListDelegate {

    private var delegate: ValueEditorDelegate? = null
    private lateinit var attribute: NumberIntervalAttribute

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        delegate = arguments?.get(Constants.DELEGATE_BUNDLE_KEY) as ValueEditorDelegate?
        attribute = arguments?.get(Constants.ATTRIBUTE_BUNDLE_KEY) as NumberIntervalAttribute? ?:
                throw Exception("Create number interval fragment with no valid attribute in arguments")

        val options = ArrayList<String>()
        for (i in attribute.minValue..attribute.maxValue)
            options.add(i.toString())

        val rootView = inflater.inflate(R.layout.number_interval_fragment, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, Constants.LIST_ITEMS_PER_ROW)
        val spacing = resources.getDimension(R.dimen.list_items_space).toInt()
        val decoration = GridSpacingItemDecoration(Constants.LIST_ITEMS_PER_ROW, spacing, false)
        recyclerView.addItemDecoration(decoration)
        recyclerView.adapter = OptionsListAdapter(options.toTypedArray(), this)

        return rootView
    }

    override fun optionAtIndexClick(index: Int) {
        delegate?.valueDidChanged(attribute.minValue + index)
    }

    companion object {
        fun newInstance(attribute: NumberIntervalAttribute,
                        delegate: ValueEditorDelegate? = null) = NumberIntervalFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constants.ATTRIBUTE_BUNDLE_KEY, attribute)
                putSerializable(Constants.DELEGATE_BUNDLE_KEY, delegate)
            }
        }
    }
}