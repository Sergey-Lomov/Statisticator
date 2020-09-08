package com.example.statisticator.models.attributes

import com.google.gson.annotations.SerializedName
import java.io.Serializable

interface EventAttributeModel: Serializable {
    val id: String
    val title: String
}