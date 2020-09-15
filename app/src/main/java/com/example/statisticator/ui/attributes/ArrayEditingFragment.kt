package com.example.statisticator.ui.attributes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.*
import java.io.Serializable

class ArrayEditingFragment : Fragment(), AttributeEditorDelegate {

    private var delegate: ValueEditorDelegate? = null
    private var initialValue: Array<Serializable>? = null
    private lateinit var currentValue: Array<Serializable?>
    private lateinit var attribute: ArrayAttribute
    private lateinit var editors: List<AttributeEditingFragment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        delegate = arguments?.get(Constants.DELEGATE_BUNDLE_KEY) as? ValueEditorDelegate
        initialValue = arguments?.get(Constants.INITIAL_VALUE_BUNDLE_KEY) as? Array<Serializable>
        attribute = arguments?.get(Constants.ATTRIBUTE_BUNDLE_KEY) as? ArrayAttribute ?:
                throw Exception("Create array editing fragment with no valid attribute in arguments")
        val optionalInitialValue = initialValue?.map { it as Serializable? }
        currentValue = optionalInitialValue?.toTypedArray() ?: arrayOfNulls(attribute.size)

        val rootView
                = inflater.inflate(R.layout.array_editing_fragment, container, false)
        editors = (0 until attribute.size).map {
            AttributeEditingFragment.newInstance(attribute.prototype,
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

    override fun attributeValueDidChanged(attribute: EventAttribute,
                                          value: Serializable,
                                          editor: AttributeEditor) {
        val index = editors.indexOf(editor)
        if (index == -1) return
        currentValue[index] = value
        val noNullValue = currentValue.filterNotNull()
        if (noNullValue.size == this.attribute.size) {
            delegate?.valueDidChanged(currentValue)
        }
    }

    companion object {
        fun newInstance(attribute: EditableAttribute,
                        initialValue: Serializable? = null,
                        delegate: ValueEditorDelegate? = null) = ArrayEditingFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constants.ATTRIBUTE_BUNDLE_KEY, attribute)
                putSerializable(Constants.INITIAL_VALUE_BUNDLE_KEY, initialValue)
                putSerializable(Constants.DELEGATE_BUNDLE_KEY, delegate)
            }
        }
    }
}