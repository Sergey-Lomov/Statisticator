package com.example.statisticator.ui.attributes

interface OptionsListDelegate {
    fun optionAtIndexClick(index: Int)
}

interface OptionsListAdapter {

    var delegate: OptionsListDelegate?
}