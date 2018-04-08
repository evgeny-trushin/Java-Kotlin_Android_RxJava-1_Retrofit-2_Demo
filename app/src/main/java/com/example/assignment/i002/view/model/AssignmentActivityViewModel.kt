package com.example.assignment.i002.view.model


import android.content.Context
import android.content.Intent
import android.databinding.ObservableField
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.widget.ArrayAdapter
import com.example.assignment.helpers.VisibilityViewModel
import com.example.assignment.i002.model.api.dto.SamplePojo
import com.example.assignment.i002.view.activity.AssignmentActivity
import java.io.Serializable

class AssignmentActivityViewModel(
        var data: List<SamplePojo>?,
        var visibilityOfProgressBar: Boolean,
        @Transient var context: Context?
) : Serializable {
    var text: String? = ""
    var position: Int = -1
    fun isProgressBarVisible() = VisibilityViewModel.visibleIfTrue(null == data)
    fun isActionVisible() = VisibilityViewModel.hiddenIfTrue(null == data)
    fun getSpinnerAdapter(context: Context): ArrayAdapter<String> {
        val array = ArrayList<String>()
        if (null != data) {
            for (datum in this.data!!) {
                array.add(datum.name)
            }
        }
        return ArrayAdapter(context, android.R.layout.simple_spinner_item, array);
    }

    var selectedState: String?
        get() = ObservableField("State").get()
        set(newSelectedValue) {
            var index = 0
            data?.forEach {
                if (it.name == newSelectedValue) {
                    if (-1 == position || position != index) {
                        text="Car: " + it.fromcentral?.car
                        if (null != it.fromcentral?.train) {
                            text+="\nTrain: " + it.fromcentral.train
                        }
                        position = index
                        (context!! as AssignmentActivity).bindViewModel(this)
                    }
                }
                index++
            }
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

