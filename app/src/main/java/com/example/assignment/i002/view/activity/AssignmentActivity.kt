package com.example.assignment.i002.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.assignment.i002.databinding.ActivityAssignmentBinding
import android.support.v7.widget.AppCompatSpinner
import android.view.View
import com.example.assignment.i002.R
import com.example.assignment.i002.view.model.AssignmentActivityViewModel
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import rx.exceptions.Exceptions

import com.example.assignment.i002.model.api.ExpressNetModel
import com.example.assignment.i002.model.api.dto.SamplePojo
import rx.android.schedulers.AndroidSchedulers


class AssignmentActivity : AppCompatActivity() {
    private var mBind: ActivityAssignmentBinding? = null
    var mViewModel = AssignmentActivityViewModel(
            "", -1, null, null, null, true
    )
    private val mStateKey = "STATE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_assignment)
        val mSpinner = (mBind?.root as View).findViewById(R.id.spinner) as AppCompatSpinner
        mSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                if (null != selectedItemView) {
                    if (mViewModel.position != position || mViewModel.position == -1) {
                        val item: SamplePojo? = mViewModel.data?.get(position)
                        mViewModel.text = "Car: " + item?.fromcentral?.car
                        if (null != item?.fromcentral?.train) {
                            mViewModel.text += "\nTrain: " + item.fromcentral.train
                        }
                        mViewModel.position = position
                        bindViewModel(mViewModel)
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }
        restoreViewModelState(savedInstanceState)
        bindViewModel(mViewModel)
        val subject = ExpressNetModel.getData()
        subject.observeOn(AndroidSchedulers.mainThread()).subscribe(
                { data ->
                    val array = ArrayList<String>()
                    for (datum in data) {
                        array.add(datum.name)
                    }
                    mViewModel.list = array
                    mViewModel.data = data
                    mViewModel.visibilityOfProgressBar = false
                    bindViewModel(mViewModel)
                }, { error -> throw Exceptions.propagate(error) }
        )
    }

    private fun restoreViewModelState(savedInstanceState: Bundle?) {
        val localViewModel = savedInstanceState?.getSerializable(mStateKey)
        if (null != localViewModel) {
            mViewModel = localViewModel as AssignmentActivityViewModel
        }
    }

    fun bindViewModel(localViewModel: AssignmentActivityViewModel) {
        mViewModel = localViewModel
        mBind?.viewModel = mViewModel
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(mStateKey, mViewModel)
    }
}