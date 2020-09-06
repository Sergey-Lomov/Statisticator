package com.example.statisticator.models

data class MenuModel (
    val id: String,
    val type: MenuType,
    val items: ArrayList<MenuItemModel> = ArrayList()
): ItemTarget {
    override val targetType: ItemTargetType
        get() = ItemTargetType.Menu
}