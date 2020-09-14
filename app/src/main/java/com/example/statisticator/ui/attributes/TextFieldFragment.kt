package com.example.statisticator.ui.attributes

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.TextFieldAttribute
import java.io.Serializable


class TextFieldFragment : Fragment() {

    private var delegate: ValueEditorDelegate? = null
    private var initialValue: String? = null
    private lateinit var attribute: TextFieldAttribute

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        delegate = arguments?.get(Constants.DELEGATE_BUNDLE_KEY) as? ValueEditorDelegate
        initialValue = arguments?.get(Constants.INITIAL_VALUE_BUNDLE_KEY) as? String
        attribute = arguments?.get(Constants.ATTRIBUTE_BUNDLE_KEY) as? TextFieldAttribute ?:
                throw Exception("Create number interval fragment with no valid attribute in arguments")

        val rootView = inflater.inflate(R.layout.text_field_fragment, container, false)
        val textField = rootView.findViewById<EditText>(R.id.textField)
        if (initialValue != null) textField.hint = initialValue
        textField.setOnEditorActionListener { textView, actionId, event ->
            handleTest(textView, actionId, event)
        }

        return rootView
    }

    private fun handleTest(textView: TextView, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
            || actionId == EditorInfo.IME_ACTION_DONE
            || event != null
            && event.action === KeyEvent.ACTION_DOWN
            && event.keyCode === KeyEvent.KEYCODE_ENTER) {
            if (event == null || !event.isShiftPressed) {
                delegate?.valueDidChanged(textView.text.toString())
                return true
            }
        }
        return false
    }

    companion object {
        fun newInstance(
            attribute: TextFieldAttribute,
            initialValue: Serializable?,
            delegate: ValueEditorDelegate? = null
        ) = TextFieldFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constants.ATTRIBUTE_BUNDLE_KEY, attribute)
                putSerializable(Constants.INITIAL_VALUE_BUNDLE_KEY, initialValue)
                putSerializable(Constants.DELEGATE_BUNDLE_KEY, delegate)
            }
        }
    }
}