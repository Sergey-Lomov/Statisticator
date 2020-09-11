package com.example.statisticator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.*
import com.example.statisticator.service.SchemaLoader


class MenuActivity : AppCompatActivity(), MenuFragmentDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuExtras = intent.extras?.get(Constants.MENU_EXTRAS_KEY) as? MenuModel
        val menu = if (menuExtras != null) {
            menuExtras
        } else {
            val schemaId = resources.getIdentifier("rk_schema", "raw", packageName)
            val stream = resources.openRawResource(schemaId)
            val json = stream.bufferedReader().use { it.readText() }
            val result = SchemaLoader().loadFromJson(json)
            result.schema.initalMenu
        }

        setContentView(R.layout.menu_activity)

        val menuFragment = supportFragmentManager.findFragmentById(R.id.menu_fragment) as? MenuFragment
        menuFragment?.updateModel(menu)
        menuFragment?.delegate = this
    }

    override fun itemClick(item: MenuItemModel) {
        val type = item.target?.targetType ?: return
        when(type) {
            ItemTargetType.Menu -> {
                val menu = item.target as? MenuModel ?: return
                showMenu(menu)
            }
            ItemTargetType.Event -> {
                val eventModel = item.target as? EventModel ?: return
                showEventEditing(eventModel)
            }
        }
    }

    private fun showMenu(model: MenuModel) {
        val intent = Intent(this@MenuActivity, MenuActivity::class.java)
        intent.putExtra(Constants.MENU_EXTRAS_KEY, model)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun showEventEditing(model: EventModel) {
        val intent = Intent(this, EventEditingActivity::class.java)
        val timestamp = System.currentTimeMillis().toString()
        val event = Event(model, timestamp)
        intent.putExtra(Constants.EVENT_EXTRAS_KEY, event)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}