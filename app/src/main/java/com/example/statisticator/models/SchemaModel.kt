package com.example.statisticator.models

data class SchemaModel(
    val initalMenu: MenuModel,
    val menus: Array<MenuModel>,
    val items: Array<MenuItemModel>
)