package com.example.statisticator.models.schema

import com.example.statisticator.models.schema.MenuItemModel
import com.example.statisticator.models.schema.MenuModel
import java.io.Serializable

data class SchemaModel (
    val initalMenu: MenuModel,
    val menus: ArrayList<MenuModel>,
    val items: ArrayList<MenuItemModel>
): Serializable