package com.example.statisticator.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.*
import com.example.statisticator.models.schema.EventModel
import com.example.statisticator.models.schema.ItemTargetType
import com.example.statisticator.models.schema.MenuItemModel
import com.example.statisticator.models.schema.MenuModel
import com.example.statisticator.service.DataStoreManager
import com.example.statisticator.service.SchemasManager


class MenuActivity : AppCompatActivity(), MenuFragmentDelegate {

    private lateinit var sessionState: SessionState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menu = intent.extras?.get(Constants.MENU_EXTRAS_KEY) as? MenuModel
            ?: SchemasManager(this).loadLastSchema().initalMenu

        val sateExtras = intent.extras?.get(Constants.SESSION_STATE_EXTRAS_KEY) as? SessionState
        sessionState = sateExtras ?: DataStoreManager(this).loadSessionState()

        setContentView(R.layout.menu_activity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val menuFragment = supportFragmentManager.findFragmentById(R.id.menu_fragment) as? MenuFragment
        menuFragment?.updateModel(menu)
        menuFragment?.delegate = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun itemClick(item: MenuItemModel) {
        val type = item.target?.targetType ?: return
        when(type) {
            ItemTargetType.Menu -> {
                val menu = item.target as? MenuModel
                    ?: return
                showMenu(menu)
            }
            ItemTargetType.Event -> {
                val eventModel = item.target as? EventModel
                    ?: return
                showEventEditing(eventModel)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Constants.EVENT_EDITING_OK) {
            val newState = data?.extras?.get(Constants.SESSION_STATE_EXTRAS_KEY) as? SessionState
            sessionState = newState ?: sessionState
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showMenu(model: MenuModel) {
        val intent = Intent(this@MenuActivity, MenuActivity::class.java)
        intent.putExtra(Constants.MENU_EXTRAS_KEY, model)
        intent.putExtra(Constants.SESSION_STATE_EXTRAS_KEY, sessionState)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun showEventEditing(model: EventModel) {
        val intent = Intent(this, EventEditingActivity::class.java)
        val timestamp = System.currentTimeMillis().toString()
        val event = Event(model, timestamp)
        intent.putExtra(Constants.EVENT_EXTRAS_KEY, event)
        intent.putExtra(Constants.SESSION_STATE_EXTRAS_KEY, sessionState)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivityForResult(intent, Constants.EVENT_EDITING_REQUEST_CODE)
    }
}