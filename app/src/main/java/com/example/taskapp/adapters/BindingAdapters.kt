package com.example.taskapp.adapters

import android.util.Log
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import com.example.taskapp.MainActivity
import com.example.taskapp.R

@BindingAdapter("onFocus")
fun bindFocusChange(
    editText: EditText,
    onFocusChangeListener: OnFocusChangeListener?
) {
    onFocusChangeListener ?: return
    editText.onFocusChangeListener = onFocusChangeListener

}

@BindingAdapter("setError")
fun setError(editText: EditText, stringOrRsrcID: Any?) {
    if (stringOrRsrcID != null) {
        when (stringOrRsrcID) {
            is String -> editText.error = stringOrRsrcID
            is Int -> editText.apply {
                val text = context.resources.getString(stringOrRsrcID)
                setError(text)
            }
        }
    }
}