package com.example.assignment.i002.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.assignment.i002.databinding.ActivityAssignmentBinding
import com.example.assignment.i002.R
import com.example.assignment.i002.view.model.AssignmentActivityViewModel
import rx.exceptions.Exceptions

import com.example.assignment.i002.model.api.ExpressNetModel
import rx.android.schedulers.AndroidSchedulers


class AssignmentActivity : AppCompatActivity() {
    private var mBind: ActivityAssignmentBinding? = null
    var mViewModel = AssignmentActivityViewModel(null, true, null)
    private val mStateKey = "STATE"

    fun bindViewModel(localViewModel: AssignmentActivityViewModel) {
        mViewModel = localViewModel
        mViewModel.context = this
        mBind?.viewModel = mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_assignment)
        restoreViewModelState(savedInstanceState)
        bindViewModel(mViewModel)
        val subject = ExpressNetModel.getData()
        subject.observeOn(AndroidSchedulers.mainThread()).subscribe(
                { data ->
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(mStateKey, mViewModel)
    }
}