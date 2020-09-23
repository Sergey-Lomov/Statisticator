package com.example.statisticator.ui.requests

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.R
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.DataRequest
import com.example.statisticator.models.schema.QueryCortegesNode
import com.example.statisticator.models.schema.QueryResultNode
import com.example.statisticator.service.RequestsState

class ResultCortegesActivity : AppCompatActivity(), GroupsListDelegate {

    private lateinit var request: DataRequest
    private lateinit var state: RequestsState
    private lateinit var result: QueryCortegesNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        request = intent.extras?.get(Constants.ExtrasKeys.Request.value) as? DataRequest
            ?: throw Exception("Create result corteges activity with no request in intent")
        state = intent.extras?.get(Constants.ExtrasKeys.RequestsState.value) as? RequestsState
            ?: throw Exception("Create result corteges activity with no requests state in intent")
        result = intent.extras?.get(Constants.ExtrasKeys.RequestResult.value) as? QueryCortegesNode
            ?: throw Exception("Create result corteges activity with no cortege result in intent")

        setContentView(R.layout.request_result_activity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ResultCortegesAdapter(result, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun resultSelect(node: QueryResultNode) {

    }
}