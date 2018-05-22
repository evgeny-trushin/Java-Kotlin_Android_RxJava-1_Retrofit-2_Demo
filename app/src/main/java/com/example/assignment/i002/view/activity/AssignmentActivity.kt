package com.example.assignment.i002.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.assignment.i002.databinding.ActivityAssignmentBinding
import com.example.assignment.i002.R
import com.example.assignment.i002.view.model.AssignmentViewModel
import rx.exceptions.Exceptions

import com.example.assignment.i002.model.api.ExpressNetModel
import rx.android.schedulers.AndroidSchedulers


class AssignmentActivity : AppCompatActivity() {
    private lateinit var mBind: ActivityAssignmentBinding
    private lateinit var mViewModel: AssignmentViewModel
    private val mStateKey = "STATE"

    fun bindViewModel(localViewModel: AssignmentViewModel) {
        mViewModel = localViewModel
        mViewModel.context = this
        mBind.layoutError?.viewModel = mViewModel
        mBind.layoutLoading?.viewModel = mViewModel
        mBind.viewModel = mViewModel
        mBind.executePendingBindings()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_assignment)
        bindViewModel(restoreViewModelState(savedInstanceState, mStateKey))
        ExpressNetModel.getData().observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    //                    Thread.sleep(10000)
                    bindViewModel(
                            AssignmentViewModel(
                                    data = it,
                                    isLoading = false,
                                    error = true)
                    )
                }, {
            error -> throw Exceptions.propagate(error) }
        )
    }

    private fun restoreViewModelState(savedInstanceState: Bundle?, stateKey: String): AssignmentViewModel {
        val localViewModel = savedInstanceState?.getSerializable(stateKey)
//        return if (null != localViewModel) {
//            localViewModel as AssignmentViewModel
//        } else {
           return AssignmentViewModel(isLoading = true, error = false)
//        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(mStateKey, mViewModel)
    }
}