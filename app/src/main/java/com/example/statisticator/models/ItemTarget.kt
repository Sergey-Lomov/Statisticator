package com.example.statisticator.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class ItemTargetType: Serializable {
    @SerializedName("menu")  Menu,
    @SerializedName("event")  Event,
}

interface ItemTarget: Serializable {
    val targetType: ItemTargetType
}