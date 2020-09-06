package com.example.statisticator.models

import java.io.Serializable

data class SchemaModel (
    val initalMenu: MenuModel,
    val menus: Array<MenuModel>,
    val items: Array<MenuItemModel>
): Serializable