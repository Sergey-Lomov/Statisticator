package com.example.statisticator.ui.requests

import android.os.Bundle
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.DataRequest
import com.example.statisticator.models.schema.attributes.Attribute
import com.example.statisticator.service.DataScopesManager
import com.example.statisticator.service.ProcessingState
import com.example.statisticator.service.RequestsState
import com.example.statisticator.ui.AttributesEditingActivity
import com.example.statisticator.ui.attributes.AttributeEditor
import java.io.Serializable

class RequestEditingActivity : AttributesEditingActivity() {

    private lateinit var request: DataRequest
    private lateinit var requestsState: RequestsState

    override val approveTitleId: Int = R.string.execute_button_title
    override val state: ProcessingState
        get() = requestsState
    override val attributes: ArrayList<Attribute>
        get() = request.query.attributes
    override val initialValues: Map<String, Serializable>
        get() = requestsState.variables

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            requestsState = intent.extras?.get(Constants.ExtrasKeys.RequestsState.value) as? RequestsState
                ?: throw Exception("Create event editing activity with no logging state in intent")
            request = intent.extras?.get(Constants.ExtrasKeys.Request.value) as? DataRequest
                ?: throw Exception("Create event editing activity with no event in intent")
        }

        super.onCreate(savedInstanceState)
    }

    override fun handleApprove() {
        val scope = DataScopesManager.shared.scopes.lastElement()
        val result = request.query.execute(requestsState, scope)
        val intent = RequestResultActivitiesFactory.intentForResult(result = result,
            context = this,
            request = request,
            state = requestsState)
        startActivity(intent)
    }

    override fun attributeValueDidChanged(attribute: Attribute, value: Serializable, editor: AttributeEditor) {
        requestsState.variables[attribute.id] = value
    }
}