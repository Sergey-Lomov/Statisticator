package com.example.statisticator.ui

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
import com.example.statisticator.models.SessionState
import com.example.statisticator.models.schema.attributes.EditableAttribute
import com.example.statisticator.models.schema.attributes.EventAttribute
import com.example.statisticator.service.EventProcessor
import com.example.statisticator.ui.attributes.AttributeEditingFragment
import com.example.statisticator.ui.attributes.AttributeEditorDelegate
import java.io.Serializable

class EventEditingActivity : AppCompatActivity(), AttributeEditorDelegate {

    private lateinit var event: Event
    private lateinit var state: SessionState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_editing_activity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (savedInstanceState == null) {
            event = intent.extras?.get(Constants.EVENT_EXTRAS_KEY) as? Event ?:
                throw Exception("Create event editing activity with no event in intent")
            state = intent.extras?.get(Constants.SESSION_STATE_EXTRAS_KEY) as? SessionState ?:
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
            val fragment = AttributeEditingFragment.newInstance(it, event.attributes[it.id],this)
            fragments.add(fragment)
        }

        val transaction = supportFragmentManager.beginTransaction()
        fragments.forEach() {
            transaction.add(R.id.linear_layout, it)
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
        val layout = findViewById<LinearLayout>(R.id.linear_layout)
        layout.addView(saveView)
    }

    private fun saveEvent() {
        EventProcessor().saveEvent(event, state, this)
        finish()
    }

    override fun attributeValueDidChanged(attribute: EventAttribute, value: Serializable) {
        event.attributes[attribute.id] = value
    }
}