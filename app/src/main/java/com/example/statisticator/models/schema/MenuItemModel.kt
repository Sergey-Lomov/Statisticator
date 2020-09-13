package com.example.statisticator.models.schema

import java.io.Serializable

data class MenuItemModel (
    val id: String,
    val title: String,
    val icon: String?,
    var target: ItemTarget? = null
) : Serializable