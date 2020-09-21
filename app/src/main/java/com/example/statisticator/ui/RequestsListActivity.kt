package com.example.statisticator.ui

import android.content.Intent
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
import com.example.statisticator.service.RequestsState
import com.example.statisticator.service.SchemasManager

class RequestsListActivity : AppCompatActivity(), RequestsListDelegate {

    private lateinit var requests: ArrayList<DataRequest>
    private lateinit var state: RequestsState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requests = intent.extras?.get(Constants.ExtrasKeys.Requests.value) as? ArrayList<DataRequest>
            ?: SchemasManager(this).loadLastSchema().requests

        val sateExtras = intent.extras?.get(Constants.ExtrasKeys.RequestsState.value) as? RequestsState
        state = sateExtras ?: RequestsState()

        setContentView(R.layout.requests_list_activity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RequestsListAdapter(requests, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun itemClick(item: DataRequest) {
        val intent = Intent(this, RequestEditingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.putExtra(Constants.ExtrasKeys.Request.value, item)
        intent.putExtra(Constants.ExtrasKeys.RequestsState.value, state)
        startActivity(intent)
    }
}