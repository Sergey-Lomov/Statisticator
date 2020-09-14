package com.example.statisticator.ui.attributes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.statisticator.R


class StringValuePresenter : ValuePresenter() {

    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.string_presenter, container, false)
        textView = rootView.findViewById(R.id.textView)
        return rootView
    }

    override fun setValue(value: Any?) {
        textView.text = value?.toString() ?: ""
    }
}