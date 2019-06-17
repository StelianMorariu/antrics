/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.commons

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.textfield.TextInputEditText
import com.stelianmorariu.antrics.R

/**
 * Created by stelian on 14/04/2018.
 */

/**
 * Inline function for adding and removing a [ViewTreeObserver.OnGlobalLayoutListener]
 */
inline fun View.waitForLayout(crossinline f: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            f()
        }
    })
}

/**
 * Inline function for adding a [ViewTreeObserver.OnGlobalLayoutListener] and removing it after
 * the view is measured.
 */
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

/**
 * TextInputEditText extensions
 */

inline fun TextInputEditText.onTextChanged(
    crossinline after: (s: Editable) -> Unit = {},
    crossinline before: (string: String, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    crossinline onTextChanged: (string: String, start: Int, count: Int, after: Int) -> Unit
) =
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) = after.invoke(s)
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) =
            before.invoke(s.toString(), start, count, after)

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) =
            onTextChanged(s.toString(), start, before, count)
    })

fun TextInputEditText.clear() = text?.clear()

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}


fun Activity.getAccentColour(): Int {
    val typedValue = TypedValue()

    val a = obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
    val color = a.getColor(0, 0)

    a.recycle()

    return color
}

fun ImageView.loadDeviceImage(imagePath: String) {
    Glide.with(this)
        .load(imagePath)
        .centerInside()
        .placeholder(R.drawable.generic_device)
        .error(R.drawable.generic_device)
        .transition(DrawableTransitionOptions.withCrossFade(500))
        .into(this)
}

fun ImageView.loadDeviceImage(resId: Int) {
    Glide.with(this)
        .load(resId)
        .centerInside()
        .transition(DrawableTransitionOptions.withCrossFade(500))
        .into(this)
}