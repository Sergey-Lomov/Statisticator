package com.example.statisticator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.attributes.EventAttributeModel
import com.example.statisticator.models.attributes.NumberIntervalAttribute
import java.io.Serializable
import kotlin.reflect.full.companionObjectInstance

interface AttributeEditorDelegate: Serializable {
    fun attributeValueDidChanged(attribute: EventAttributeModel, value: Serializable)
}

interface ValueEditorDelegate: Serializable {
    fun valueDidChanged(value: Serializable)
}

class AttributeEditingFragment : Fragment(), ValueEditorDelegate {

    private var delegate: AttributeEditorDelegate? = null
    private lateinit var attribute: EventAttributeModel
    private lateinit var valueTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        delegate = arguments?.get(Constants.DELEGATE_BUNDLE_KEY) as AttributeEditorDelegate?
        attribute = arguments?.get(Constants.ATTRIBUTE_BUNDLE_KEY) as EventAttributeModel? ?:
                throw Exception("Create attribute editing fragment with no attribute in arguments")

        val rootView = inflater.inflate(R.layout.attribute_fragment, container, false)
        val titleTextView = rootView.findViewById<TextView>(R.id.title)
        titleTextView.text = attribute.title
        valueTextView = rootView.findViewById(R.id.value)

        val fragment = when (attribute) {
            is NumberIntervalAttribute -> NumberIntervalFragment.newInstance(attribute as NumberIntervalAttribute, this)
            else -> throw Exception("Can't found value editing fragment for attribute")
        }

        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.frame_layout, fragment)
        transaction.commit()

        return rootView
    }

    override fun valueDidChanged(value: Serializable) {
        valueTextView.text = value.toString()
        delegate?.attributeValueDidChanged(attribute, value)
    }

    companion object {
        fun newInstance(attribute: EventAttributeModel,
                        delegate: AttributeEditorDelegate? = null) = AttributeEditingFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constants.ATTRIBUTE_BUNDLE_KEY, attribute)
                putSerializable(Constants.DELEGATE_BUNDLE_KEY, delegate)
            }
        }
    }
}