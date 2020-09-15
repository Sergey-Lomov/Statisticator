package com.example.statisticator.ui.attributes

class ArrayValuePresenter: StringValuePresenter() {

    override fun stringForValue(value: Any?): String {
        return if (value == null) "-" else "+"
    }
}