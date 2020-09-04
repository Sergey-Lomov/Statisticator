package com.example.statisticator.models

data class MenuItemModel (
    val id: String,
    val title: String,
    var target: ItemTarget? = null
)