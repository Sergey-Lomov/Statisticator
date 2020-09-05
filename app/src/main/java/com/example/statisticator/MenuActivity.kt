package com.example.statisticator

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.statisticator.models.ItemTargetType
import com.example.statisticator.models.MenuItemModel
import com.example.statisticator.models.MenuModel
import com.example.statisticator.models.SchemaModel
import com.example.statisticator.service.SchemaLoader
import java.util.*
import kotlin.concurrent.schedule


class MenuActivity : AppCompatActivity(), MenuFragmentDelegate {

    private lateinit var schema: SchemaModel
    private val CONTENT_VIEW_ID = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val schemaId = resources.getIdentifier("rk_schema", "raw", packageName)
        val stream = resources.openRawResource(schemaId)
        val json = stream.bufferedReader().use { it.readText() }
        schema = SchemaLoader().loadFromJson(json)

        //setContentView(R.layout.activity_main)
        val frame = FrameLayout(this)
        frame.id = CONTENT_VIEW_ID
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        setContentView(frame, params)

        if (savedInstanceState == null) {
            showMenu(schema.initalMenu, false)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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
        val newFragment: MenuFragment = MenuFragment.newInstance(menu)
        newFragment.delegate = this
//        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        if (animated)
//            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
//        else
//            transaction.setCustomAnimations(0, R.anim.slide_out_to_left)
//        transaction.replace(CONTENT_VIEW_ID, newFragment).addToBackStack(null).commit()
        supportFragmentManager.beginTransaction()
            .replace(CONTENT_VIEW_ID, newFragment)
            .addToBackStack(null)
            .commit()

//        Timer("SettingUp", false).schedule(1000) {
//            showMenu(menu, true)
//        }
    }
}