package com.example.statisticator.models.schema

import java.io.Serializable

data class SchemaModel (
    val initalMenu: MenuModel,
    val menus: ArrayList<MenuModel>,
    val items: ArrayList<MenuItemModel>,
    val requests: ArrayList<DataRequest>
): Serializable