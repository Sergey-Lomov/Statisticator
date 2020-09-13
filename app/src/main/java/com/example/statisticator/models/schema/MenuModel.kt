package com.example.statisticator.models.schema

data class MenuModel (
    val id: String,
    val style: MenuStyle,
    val items: ArrayList<MenuItemModel> = ArrayList()
): ItemTarget {
    override val targetType: ItemTargetType
        get() = ItemTargetType.Menu
}