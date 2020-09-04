package com.example.statisticator.service

import com.example.statisticator.models.*
import com.google.gson.Gson

class SchemaLoader {

    data class MenuItemPrototype (val id: String, val title: String, val type: ItemTargetType?, val target: String?)
    data class MenuPrototype(val id: String, val type: MenuType?, val items: Array<String>)
    data class SchemaPrototype (val menus: Array<MenuPrototype>, val items: Array<MenuItemPrototype>, val initialMenu: String)

    fun loadFromJson(json: String): SchemaModel {
        val schemaPrototype = Gson().fromJson(json, SchemaPrototype::class.java)

        val itemsToPrototypes = schemaPrototype.items.associateBy({ MenuItemModel(it.id, it.title) }, {it})
        val items = itemsToPrototypes.keys
        val menusToPrototypes = schemaPrototype.menus.associateBy({ MenuModel(it.id, it.type ?: MenuType.Full) }, {it})
        val menus = menusToPrototypes.keys

        menus.forEach {menuModel ->
            val itemsIds = menusToPrototypes[menuModel]?.items ?: emptyArray()
            val itItems = items.filter { itemsIds.contains(it.id) }
            menuModel.items.addAll(itItems)
        }

        items.forEach each@{item ->
            val prototype = itemsToPrototypes[item] ?: return@each
            val type = prototype.type ?: return@each
            when (type) {
                ItemTargetType.Menu -> item.target = menus.first { it.id == prototype.target }
            }
        }

        val initialMenu = menus.first { it.id == schemaPrototype.initialMenu }

        return SchemaModel(initialMenu, menus.toTypedArray(), items.toTypedArray())
    }
}