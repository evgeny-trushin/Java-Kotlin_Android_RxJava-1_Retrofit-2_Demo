package com.example.assignment.i002.view.model


import android.R
import android.content.Context

import java.io.Serializable
import android.os.Parcelable
import com.example.assignment.i002.view.activity.AssignmentActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.assignment.helpers.VisibilityViewModel


class AssignmentActivityViewModel(
        var text: String,
        val list: MutableList<String>,
        var visibilityOfProgressBar: Boolean
) : Serializable {
    var mViewPagerState: Parcelable? = null
    fun isProgressBarVisible() = VisibilityViewModel.getVisibleIfTrue(visibilityOfProgressBar)
    fun getSpinnerAdapter(context: Context): ArrayAdapter<String> {
       return ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
    }
    fun clickOnButton(context: Context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        val viewModel = (context as AssignmentActivity).viewModel
        viewModel.visibilityOfProgressBar = false
        viewModel.text = "test"
        context.bindViewModel(viewModel)
    }
}

