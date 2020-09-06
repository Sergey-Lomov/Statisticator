package com.example.statisticator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.statisticator.models.ItemTargetType
import com.example.statisticator.models.MenuItemModel
import com.example.statisticator.models.MenuModel
import com.example.statisticator.service.SchemaLoader


class MenuActivity : AppCompatActivity(), MenuFragmentDelegate {

    private val MENU_EXTRAS_KEY = "menuModel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuExtras = intent.extras?.get(MENU_EXTRAS_KEY) as? MenuModel
        val menu = if (menuExtras != null) {
            menuExtras
        } else {
            val schemaId = resources.getIdentifier("rk_schema", "raw", packageName)
            val stream = resources.openRawResource(schemaId)
            val json = stream.bufferedReader().use { it.readText() }
            val schema = SchemaLoader().loadFromJson(json)
            schema.initalMenu
        }

        setContentView(R.layout.menu_activity)

        val menuFragment = supportFragmentManager.findFragmentById(R.id.menu_fragment) as? MenuFragment
        menuFragment?.updateModel(menu)
        menuFragment?.delegate = this
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
       // menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun itemClick(item: MenuItemModel) {
        val type = item.target?.targetType ?: return
        when(type) {
            ItemTargetType.Menu -> {
                val menu = item.target as? MenuModel ?: return
                showMenu(menu)
            }
        }
    }

    private fun showMenu(menu: MenuModel, animated: Boolean = true) {
        val intent = Intent(this@MenuActivity, MenuActivity::class.java)
        intent.putExtra(MENU_EXTRAS_KEY, menu)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}