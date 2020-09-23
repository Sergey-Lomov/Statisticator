package com.example.statisticator.ui.requests

import android.content.Context
import android.content.Intent
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.DataRequest
import com.example.statisticator.models.schema.QueryCortegesNode
import com.example.statisticator.models.schema.QueryGroupNode
import com.example.statisticator.models.schema.QueryResultNode
import com.example.statisticator.service.RequestsState

class RequestResultActivitiesFactory {

    companion object {
        fun intentForResult(context: Context,
                            result: QueryResultNode,
                            request: DataRequest,
                            state: RequestsState): Intent {
            val destinationClass = when (result) {
                is QueryGroupNode -> ResultGroupsActivity::class
                is QueryCortegesNode -> ResultCortegesActivity::class
                else -> error("Unsupported type or QueryResultNode at intent creation")
            }
            val intent = Intent(context, destinationClass.java)
            intent.putExtra(Constants.ExtrasKeys.Request.value, request)
            intent.putExtra(Constants.ExtrasKeys.RequestResult.value, result)
            intent.putExtra(Constants.ExtrasKeys.RequestsState.value, state)
            return intent
        }
    }
}