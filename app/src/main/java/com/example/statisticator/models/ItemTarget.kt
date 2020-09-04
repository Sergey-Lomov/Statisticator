package com.example.statisticator.models

import com.google.gson.annotations.SerializedName

enum class ItemTargetType {
    @SerializedName("menu")  Menu,
    @SerializedName("event")  Event,
}

interface ItemTarget {
    val targetType: ItemTargetType
}