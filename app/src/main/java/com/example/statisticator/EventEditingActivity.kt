package com.example.statisticator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.Event
import com.example.statisticator.models.attributes.EventAttribute
import com.example.statisticator.service.DataStoreManager
import java.io.Serializable

class EventEditingActivity : AppCompatActivity(), AttributeEditorDelegate {

    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_editing_activity)

        event = intent.extras?.get(Constants.EVENT_EXTRAS_KEY) as? Event ?:
                throw Exception("Create event editing activity with no event in intent")

        val transaction = supportFragmentManager.beginTransaction()
        event.model.attributes.forEach() {
            val fragment = AttributeEditingFragment.newInstance(it, event.attributes[it.id],this)
            transaction.add(R.id.linear_layout, fragment)
        }
        transaction.runOnCommit {
            val saveView = LayoutInflater.from(this).inflate(R.layout.save_button_item, null, false)
            val saveButton = saveView.findViewById<Button>(R.id.saveButton)
            saveButton.setOnClickListener { saveEvent() }
            val layout = findViewById<LinearLayout>(R.id.linear_layout)
            layout.addView(saveView)
        }
        transaction.commit()
    }

    private fun saveEvent() {
        DataStoreManager(this).saveEvent(event)
        finish()
    }

    override fun attributeValueDidChanged(attribute: EventAttribute, value: Serializable) {
        event.attributes[attribute.id] = value
    }
}