package com.example.assignment.i002.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.assignment.i002.databinding.ActivityAssignmentBinding
import com.example.assignment.i002.R
import com.example.assignment.i002.view.model.AssignmentViewModel

class AssignmentActivity : AppCompatActivity() {
    private lateinit var mBind: ActivityAssignmentBinding
    private lateinit var mViewModel: AssignmentViewModel
    private val mStateKey = "STATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_assignment)
        bindViewModel(restoreViewModelState(savedInstanceState, mStateKey))
        AssignmentViewModel(context = this).getData()
    }

    fun bindViewModel(viewModel: AssignmentViewModel) {
        mViewModel = viewModel
        mViewModel.context = this
        mBind.apply {
            layoutError?.viewModel = mViewModel
            layoutLoading?.viewModel = mViewModel
            this.viewModel = mViewModel
            executePendingBindings()
        }
    }

    fun bindThrowableToViewModel(error: Throwable) {
        bindViewModel(
                AssignmentViewModel().apply {
                    setExceptionAsErrorCode(error)
                })
    }

    private fun restoreViewModelState(savedInstanceState: Bundle?, stateKey: String) =
            savedInstanceState?.getSerializable(stateKey) as AssignmentViewModel
            ??:AssignmentViewModel(isLoading = true, error = false)

            override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(mStateKey, mViewModel)
    }
}