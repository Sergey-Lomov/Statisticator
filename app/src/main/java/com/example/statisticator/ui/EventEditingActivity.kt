package com.example.statisticator.ui

import android.content.Intent
import android.os.Bundle
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.Event
import com.example.statisticator.service.LoggingState
import com.example.statisticator.models.schema.attributes.Attribute
import com.example.statisticator.service.EventProcessor
import com.example.statisticator.service.ProcessingState
import com.example.statisticator.ui.attributes.AttributeEditor
import java.io.Serializable

class EventEditingActivity : AttributesEditingActivity() {

    private lateinit var event: Event
    private lateinit var loggingState: LoggingState

    override val approveTitleId: Int = R.string.save_button_title
    override val state: ProcessingState
        get() = loggingState
    override val attributes: ArrayList<Attribute>
        get() = event.model.attributes
    override val initialValues: Map<String, Serializable>
        get() = event.attributes

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            loggingState = intent.extras?.get(Constants.ExtrasKeys.LoggingState.value) as? LoggingState
                ?: throw Exception("Create event editing activity with no logging state in intent")
            event = intent.extras?.get(Constants.ExtrasKeys.Event.value) as? Event
                ?: throw Exception("Create event editing activity with no event in intent")
        }

        super.onCreate(savedInstanceState)
    }

    override fun handleApprove() {
        EventProcessor().saveEvent(event, loggingState, this)
        val resultIntent = Intent()
        resultIntent.putExtra(Constants.ExtrasKeys.LoggingState.value, state)
        setResult(Constants.EVENT_EDITING_OK, resultIntent)
        finish()
    }

    override fun attributeValueDidChanged(attribute: Attribute, value: Serializable, editor: AttributeEditor) {
        event.attributes[attribute.id] = value
    }
}