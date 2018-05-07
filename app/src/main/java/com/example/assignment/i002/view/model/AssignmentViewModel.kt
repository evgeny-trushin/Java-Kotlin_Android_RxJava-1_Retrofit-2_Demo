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
import com.example.assignment.i002.view.operation.ModelViewError
import java.io.Serializable

data class AssignmentViewModel(
        var data: List<SamplePojo>?,
        @Transient var context: Context?
) : Serializable, ModelViewError {

    var text: String? = ""
    private var position: Int? = -1

    override fun isContentVisible()  = VisibilityViewModel.hiddenIfTrue(error)

    override fun isErrorVisible()  = VisibilityViewModel.visibleIfTrue(error)

    override var error: Boolean = true

//    override fun error(): Int {
//        ret
//    }

    override fun clickOnRetryButton(context:Context) {
        this.error = false
        (context!! as AssignmentActivity).bindViewModel(this)
    }


    var proxyPosition: Int?
        get() = ObservableField<Int>(position).get()
        set(newSelectedPosition) {
            if (-1 == position || position != newSelectedPosition) {
                val it = data?.get(newSelectedPosition!!)
                text = "Car: " + it?.fromcentral?.car
                if (null != it?.fromcentral?.train) {
                    text += "\nTrain: " + it.fromcentral.train
                }
                position = newSelectedPosition
                (context!! as AssignmentActivity).bindViewModel(this)
            }
        }

    fun isProgressBarVisible() = VisibilityViewModel.hiddenIfTrue(null == data)
    fun isActionVisible() = VisibilityViewModel.hiddenIfTrue(null == data)
    fun getSpinnerAdapter(context: Context): ArrayAdapter<String> {
        val array = ArrayList<String>()
        if (null != data) {
            for (datum in this.data!!) {
                array.add(datum.name)
            }
        }
        return ArrayAdapter(context, android.R.layout.simple_spinner_item, array)
    }
    fun clickOnButton(context: Context) {
        val location = (data?.get(proxyPosition!!) as SamplePojo).location
        val gmmIntentUri = Uri.parse("geo:" + location.latitude + "," + location.longitude)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.`package` = "com.google.android.apps.maps"
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(context, mapIntent, null)
        }
    }
}