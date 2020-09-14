package com.example.statisticator.ui.attributes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.*
import java.io.Serializable

interface AttributeEditorDelegate: Serializable {
    fun attributeValueDidChanged(attribute: EventAttribute, value: Serializable)
}

interface ValueEditorDelegate: Serializable {
    fun valueDidChanged(value: Serializable)
}

class AttributeEditingFragment : Fragment(), ValueEditorDelegate {

    private var delegate: AttributeEditorDelegate? = null
    private var initialValue: Serializable? = null
    private lateinit var attribute: EditableAttribute
    private lateinit var valueTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        delegate = arguments?.get(Constants.DELEGATE_BUNDLE_KEY) as? AttributeEditorDelegate
        initialValue = arguments?.get(Constants.INITIAL_VALUE_BUNDLE_KEY) as? Serializable
        attribute = arguments?.get(Constants.ATTRIBUTE_BUNDLE_KEY) as? EditableAttribute ?:
                throw Exception("Create attribute editing fragment with no attribute in arguments")

        val rootView = inflater.inflate(R.layout.attribute_fragment, container, false)
        val titleTextView = rootView.findViewById<TextView>(R.id.title)
        titleTextView.text = attribute.title
        valueTextView = rootView.findViewById(R.id.value)
        valueTextView.text = if (initialValue != null) initialValue!!.toString() else Constants.EMPTY_VALUE_STUB

        val fragment = when (attribute) {
            is NumberIntervalAttribute -> NumberIntervalFragment.newInstance(attribute as NumberIntervalAttribute,
                this)
            is TextFieldAttribute -> TextFieldFragment.newInstance(attribute as TextFieldAttribute,
                initialValue,
                this)
            is ColorsListAttribute -> ColorsListFragment.newInstance(attribute as ColorsListAttribute,
                this)
            else -> throw Exception("Can't found value editing fragment for attribute")
        }

        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.frame_layout, fragment)
        transaction.commit()

        return rootView
    }

    override fun valueDidChanged(value: Serializable) {
        valueTextView.text = value.toString()
        delegate?.attributeValueDidChanged(attribute, value)
    }

    companion object {
        fun newInstance(attribute: EditableAttribute,
                        initialValue: Serializable? = null,
                        delegate: AttributeEditorDelegate? = null) = AttributeEditingFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constants.ATTRIBUTE_BUNDLE_KEY, attribute)
                putSerializable(Constants.INITIAL_VALUE_BUNDLE_KEY, initialValue)
                putSerializable(Constants.DELEGATE_BUNDLE_KEY, delegate)
            }
        }
    }
}