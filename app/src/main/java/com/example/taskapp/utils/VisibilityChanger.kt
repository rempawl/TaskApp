package com.example.taskapp.utils

import android.view.View
import androidx.core.view.isVisible

object VisibilityChanger {

    fun changeViewsHelper(currentViews: List<View>?, allViews: List<View>) {
        val currentList = currentViews ?: emptyList()
        changeViewsVisibility(currentList, allViews - currentList)
    }

    private fun <T : View> changeViewsVisibility(
        visibleViews: List<T>,
        goneViews: List<T>
    ) {
        visibleViews.forEach { view ->
            if (!view.isVisible) view.visibility = View.VISIBLE
        }
        goneViews.forEach { view ->
            if (view.isVisible) view.visibility = View.GONE
        }

    }

}