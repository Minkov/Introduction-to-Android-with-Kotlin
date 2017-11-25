package com.minkov.app

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

/**
 * Created by doncho on 11/25/17.
 */
fun AppCompatActivity.showLoading(loaderId: Int, contentId: Int) {
    val loaderContainer = findViewById<ViewGroup>(loaderId)
    loaderContainer.removeAllViews()
    val loader = ProgressBar(this)

    loaderContainer.addView(loader)
    loaderContainer.visibility = View.VISIBLE

    findViewById<View>(contentId).visibility = View.GONE
}

fun AppCompatActivity.hideLoading(loaderId: Int, contentId: Int) {
    findViewById<View>(loaderId).visibility = View.GONE
    findViewById<View>(contentId).visibility = View.VISIBLE
}