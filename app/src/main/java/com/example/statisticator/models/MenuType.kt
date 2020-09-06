package com.example.statisticator.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class MenuType: Serializable {
    @SerializedName("full")  Full,
    @SerializedName("short")  Short,
}
