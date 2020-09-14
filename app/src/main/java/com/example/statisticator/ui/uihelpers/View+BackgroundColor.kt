package com.example.statisticator.ui.uihelpers

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.View

fun View.setDrawableBackground(color: Int) {
    when (background) {
        is ShapeDrawable -> (background as? ShapeDrawable)?.paint?.color = color
        is GradientDrawable -> (background as? GradientDrawable)?.setColor(color)
        is ColorDrawable -> (background as? ColorDrawable)?.color = color
    }
}