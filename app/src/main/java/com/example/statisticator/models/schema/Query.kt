package com.example.statisticator.models.schema

import com.example.statisticator.models.AttributesContainer
import com.example.statisticator.models.schema.attributes.Attribute
import com.example.statisticator.service.DataScope
import com.example.statisticator.service.RequestsState
import com.example.statisticator.service.factories.TypedEntitiesOwner
import java.io.Serializable
import kotlin.collections.ArrayList

interface QueryResultNode : Serializable {}

data class QueryGroupNode(
    val title: String,
    val content: Map<Serializable, QueryResultNode>
) : QueryResultNode

data class QueryCortegesNode(
    val content: Iterable<AttributesContainer>
) : QueryResultNode

data class QueryValueNode(
    val title: String,
    val content: Serializable
) : QueryResultNode

class Query(
    override val id: String,
    val attributes: ArrayList<Attribute> = ArrayList()
) : TypedEntitiesOwner {
    fun execute(state: RequestsState, scope: DataScope): QueryResultNode {
        val g1ac1 = AttributesContainer()
        g1ac1.content["Бонус"] = 5
        g1ac1.content["МК"] = 0
        g1ac1.content["Всего"] = 8

        val g1ac2 = AttributesContainer()
        g1ac2.content["Бонус"] = 4
        g1ac2.content["МК"] = 3
        g1ac2.content["Всего"] = 10

        val g1ac3 = AttributesContainer()
        g1ac3.content["Бонус"] = 0
        g1ac3.content["МК"] = 12
        g1ac3.content["Всего"] = 15

        val at1 = QueryCortegesNode(listOf(g1ac1, g1ac2, g1ac3))

        val g2ac1 = AttributesContainer()
        g2ac1.content["Бонус"] = 0
        g2ac1.content["МК"] = 0
        g2ac1.content["Всего"] = 6

        val g2ac2 = AttributesContainer()
        g2ac2.content["Бонус"] = 1
        g2ac2.content["МК"] = 3
        g2ac2.content["Всего"] = 10

        val g2ac3 = AttributesContainer()
        g2ac3.content["Бонус"] = 0
        g2ac3.content["МК"] = 12
        g2ac3.content["Всего"] = 18

        val at2 = QueryCortegesNode(listOf(g2ac1, g2ac2, g2ac3))

        val igc = mapOf<Serializable, QueryResultNode>(1 to at1, 2 to at2)
        val ig = QueryGroupNode("Рудиус добычи", igc)

        val ogc = mapOf<Serializable, QueryResultNode>("Аня" to ig, "Вася" to ig)
        val og = QueryGroupNode("Игрок", ogc)

        return og
    }
}