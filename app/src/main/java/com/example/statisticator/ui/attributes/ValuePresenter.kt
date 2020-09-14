package com.example.statisticator.ui.attributes

import androidx.fragment.app.Fragment

abstract class ValuePresenter: Fragment() {

    abstract fun setValue(value: Any?)
}