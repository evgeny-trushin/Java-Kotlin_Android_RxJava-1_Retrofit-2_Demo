package com.example.assignment.i002.view.model


import android.content.Context

import java.io.Serializable
import android.os.Parcelable
import android.widget.ArrayAdapter
import com.example.assignment.helpers.VisibilityViewModel
import com.example.assignment.i002.model.api.dto.SamplePojo
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri


class AssignmentActivityViewModel(
        var text: String,
        var position: Int,
        @Transient var spinnerState: Parcelable?,
        var list: MutableList<String>?,
        var data: List<SamplePojo>?,
        var visibilityOfProgressBar: Boolean
) : Serializable {
    fun isProgressBarVisible() = VisibilityViewModel.visibleIfTrue(null == list)
    fun isActionVisible() = VisibilityViewModel.hiddenIfTrue(null == list)
    fun getSpinnerAdapter(context: Context): ArrayAdapter<String> {
        return ArrayAdapter(context, android.R.layout.simple_spinner_item, if (null == list) {
            ArrayList()
        } else list)
    }

    fun clickOnButton(context: Context) {
        val location = (data?.get(position) as SamplePojo).location
        val gmmIntentUri = Uri.parse("geo:" + location.latitude + "," + location.longitude)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.`package` = "com.google.android.apps.maps"
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(context, mapIntent, null)
        }
    }
}

