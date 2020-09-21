package com.example.statisticator.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.Event
import com.example.statisticator.models.LoggingState
import com.example.statisticator.models.schema.attributes.EditableAttribute
import com.example.statisticator.models.schema.attributes.Attribute
import com.example.statisticator.service.EventProcessor
import com.example.statisticator.ui.attributes.AttributeEditingFragment
import com.example.statisticator.ui.attributes.AttributeEditor
import com.example.statisticator.ui.attributes.AttributeEditorDelegate
import java.io.Serializable

class EventEditingActivity : AppCompatActivity(), AttributeEditorDelegate {

    private lateinit var event: Event
    private lateinit var state: LoggingState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_editing_activity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (savedInstanceState == null) {
            event = intent.extras?.get(Constants.ExtrasKeys.Event.value) as? Event ?:
                throw Exception("Create event editing activity with no event in intent")
            state = intent.extras?.get(Constants.ExtrasKeys.LoggingState.value) as? LoggingState ?:
                throw Exception("Create event editing activity with no session state in intent")
            setupUI()
        } else {
            addSaveButton()
        }
    }

    private fun setupUI () {
        val fragments: MutableList<Fragment> = mutableListOf()
        val editable = event.model.attributes.filterIsInstance<EditableAttribute>()
        editable.forEach() {
            val fragment = AttributeEditingFragment.newInstance(it, state, event.attributes[it.id],this)
            fragments.add(fragment)
        }

        val transaction = supportFragmentManager.beginTransaction()
        fragments.forEach() {
            transaction.add(R.id.linearLayout, it)
        }
        transaction.runOnCommit {
            addSaveButton()
        }
        transaction.commit()
    }

    private fun addSaveButton() {
        val saveView = LayoutInflater.from(this).inflate(R.layout.save_button_item, null, false)
        val saveButton = saveView.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener { saveEvent() }
        val layout = findViewById<LinearLayout>(R.id.linearLayout)
        layout.addView(saveView)
    }

    private fun saveEvent() {
        EventProcessor().saveEvent(event, state, this)
        val resultIntent = Intent()
        resultIntent.putExtra(Constants.ExtrasKeys.LoggingState.value, state)
        setResult(Constants.EVENT_EDITING_OK, resultIntent)
        finish()
    }

    override fun attributeValueDidChanged(attribute: Attribute, value: Serializable, editor: AttributeEditor) {
        event.attributes[attribute.id] = value
    }
}