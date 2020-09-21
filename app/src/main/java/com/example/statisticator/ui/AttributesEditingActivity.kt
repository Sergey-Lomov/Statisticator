package com.example.statisticator.ui

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.statisticator.R
import com.example.statisticator.models.schema.attributes.EditableAttribute
import com.example.statisticator.models.schema.attributes.Attribute
import com.example.statisticator.service.ProcessingState
import com.example.statisticator.ui.attributes.AttributeEditingFragment
import com.example.statisticator.ui.attributes.AttributeEditorDelegate
import java.io.Serializable

abstract class AttributesEditingActivity : AppCompatActivity(), AttributeEditorDelegate {

    abstract protected val state: ProcessingState
    abstract protected val attributes: ArrayList<Attribute>
    abstract protected val initialValues: Map<String, Serializable>
    abstract protected val approveTitleId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attributes_editing_activity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (savedInstanceState == null) {
            setupUI()
        } else {
            addApproveButton()
        }
    }

    private fun setupUI () {
        val fragments: MutableList<Fragment> = mutableListOf()
        val editable = attributes.filterIsInstance<EditableAttribute>()
        editable.forEach() {
            val fragment = AttributeEditingFragment.newInstance(it, state, initialValues[it.id],this)
            fragments.add(fragment)
        }

        val transaction = supportFragmentManager.beginTransaction()
        fragments.forEach() {
            transaction.add(R.id.linearLayout, it)
        }
        transaction.runOnCommit {
            addApproveButton()
        }
        transaction.commit()
    }

    private fun addApproveButton() {
        val approveView = LayoutInflater.from(this).inflate(R.layout.save_button_item, null, false)
        val approveButton = approveView.findViewById<Button>(R.id.approveButton)
        approveButton.text = getString(approveTitleId)
        approveButton.setOnClickListener { handleApprove() }
        val layout = findViewById<LinearLayout>(R.id.linearLayout)
        layout.addView(approveView)
    }

    abstract protected fun handleApprove()
}