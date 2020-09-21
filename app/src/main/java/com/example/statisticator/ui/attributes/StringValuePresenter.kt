package com.example.statisticator.ui.attributes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.statisticator.R

open class StringValuePresenter : ValuePresenter() {

    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.string_presenter, container, false)
        textView = rootView.findViewById(R.id.title)
        return rootView
    }

    override fun setValue(value: Any?) {
        textView.text = stringForValue(value)
    }

    open fun stringForValue(value: Any?): String {
        return value?.toString() ?: ""
    }
}