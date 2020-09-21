package com.example.statisticator.ui.attributes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.LoggingState
import com.example.statisticator.models.schema.attributes.*
import java.io.Serializable

class ArrayEditingFragment : Fragment(), AttributeEditorDelegate {

    private var delegate: ValueEditorDelegate? = null
    private var initialValue: Array<Serializable>? = null
    private lateinit var currentValue: Array<Serializable?>
    private lateinit var attribute: ArrayAttribute
    private lateinit var editors: List<AttributeEditingFragment>
    private var size: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        delegate = arguments?.get(Constants.DELEGATE_BUNDLE_KEY) as? ValueEditorDelegate
        initialValue = arguments?.get(Constants.INITIAL_VALUE_BUNDLE_KEY) as? Array<Serializable>
        val state = arguments?.get(Constants.SESSION_STATE_BUNDLE_KEY) as? LoggingState
            ?: throw Exception("Create array editing fragment with no session state in arguments")
        attribute = arguments?.get(Constants.ATTRIBUTE_BUNDLE_KEY) as? ArrayAttribute
            ?: throw Exception("Create array editing fragment with no valid attribute in arguments")

        size = when (attribute) {
            is StaticArrayAttribute -> (attribute as? StaticArrayAttribute)?.size ?: 0
            is DynamicArrayAttribute -> {
                val sizeVar = (attribute as? DynamicArrayAttribute)?.sizeVar ?: ""
                state.variables[sizeVar] as? Int ?: 0
            }
            else -> 0
        }

        val optionalInitialValue = initialValue?.map { it as Serializable? }
        currentValue = optionalInitialValue?.toTypedArray() ?: arrayOfNulls(size)

        val rootView = inflater.inflate(R.layout.array_editing_fragment, container, false)
        editors = (0 until size).map {
            AttributeEditingFragment.newInstance(attribute.prototype,
                state = state,
                initialValue = initialValue?.get(it),
                delegate = this,
                predefinedTitle = (it + 1).toString())
        }

        val transaction = childFragmentManager.beginTransaction()
        editors.forEach {
            transaction.add(R.id.linearLayout, it)
        }
        transaction.commit()

        return rootView
    }

    override fun attributeValueDidChanged(attribute: Attribute,
                                          value: Serializable,
                                          editor: AttributeEditor) {
        val index = editors.indexOf(editor)
        if (index == -1) return
        currentValue[index] = value
        val noNullValue = currentValue.filterNotNull()
        if (noNullValue.size == size) {
            delegate?.valueDidChanged(currentValue)
        }
    }

    companion object {
        fun newInstance(attribute: EditableAttribute,
                        state: LoggingState,
                        initialValue: Serializable? = null,
                        delegate: ValueEditorDelegate? = null) = ArrayEditingFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constants.ATTRIBUTE_BUNDLE_KEY, attribute)
                putSerializable(Constants.SESSION_STATE_BUNDLE_KEY, state)
                putSerializable(Constants.INITIAL_VALUE_BUNDLE_KEY, initialValue)
                putSerializable(Constants.DELEGATE_BUNDLE_KEY, delegate)
            }
        }
    }
}