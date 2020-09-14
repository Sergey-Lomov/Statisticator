package com.example.statisticator.ui.attributes

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.statisticator.R
import com.example.statisticator.ui.uihelpers.setDrawableBackground


class ColorValuePresenter : ValuePresenter() {

    private lateinit var colorView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.color_presenter, container, false)
        colorView = rootView.findViewById(R.id.colorView)
        return rootView
    }

    override fun setValue(value: Any?) {
        val color = value as? Int ?: Color.TRANSPARENT
        colorView.setDrawableBackground(color)
    }
}