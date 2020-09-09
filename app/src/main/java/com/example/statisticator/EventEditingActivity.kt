package com.example.statisticator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.Event
import com.example.statisticator.models.attributes.EventAttributeModel
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
            val fragment = AttributeEditingFragment.newInstance(it, this)
            transaction.add(R.id.linear_layout, fragment)
        }
        transaction.commit()
    }

    override fun attributeValueDidChanged(attribute: EventAttributeModel, value: Serializable) {
        event.attributes[attribute.id] = value
    }
}