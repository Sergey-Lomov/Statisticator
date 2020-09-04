package com.example.statisticator.models

import com.google.gson.annotations.SerializedName

data class MenuModel (
    val id: String,
    val type: MenuType,
    val items: ArrayList<MenuItemModel> = ArrayList()
): ItemTarget {
    override val targetType: ItemTargetType
        get() = ItemTargetType.Menu
}