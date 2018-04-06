package com.example.assignment.i002.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.assignment.i002.databinding.ActivityAssignmentBinding
import me.relex.circleindicator.CircleIndicator
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatSpinner
import android.view.View
import com.example.assignment.helpers.ViewPagerModified
import com.example.assignment.i002.R
import com.example.assignment.i002.view.model.AssignmentActivityViewModel
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import rx.exceptions.Exceptions
import rx.subjects.ReplaySubject
import com.example.assignment.helpers.ApiAbstractFactory
import android.R.attr.countDown

import rx.observers.TestSubscriber

import com.example.assignment.i002.model.api.ExpressNetModel
import com.example.assignment.i002.model.api.dto.Pojo


class AssignmentActivity : AppCompatActivity() {
    private var mBind: ActivityAssignmentBinding? = null
    var viewModel = AssignmentActivityViewModel(
            "Car - 40 mins \nTrain - 40 mins", arrayListOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5"), true
    )
    private val mStateKey = "STATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_assignment)
        val spinner = (mBind?.root as View).findViewById(R.id.spinner) as AppCompatSpinner
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {

                Toast.makeText(selectedItemView.context, viewModel.list[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }
        restoreViewModelState(savedInstanceState)
        mBind?.viewModel = viewModel

        val subject = ExpressNetModel.getData()
        val subscriber = TestSubscriber<Pojo>()
        subject.subscribe({ ok ->
            subject.subscribe(subscriber)
        }, { error ->
            throw Exceptions.propagate(error)
        }
        )
    }

    private fun restoreViewModelState(savedInstanceState: Bundle?) {
        val localViewModel = savedInstanceState?.getSerializable(mStateKey)
        if (null != localViewModel) {
            viewModel = localViewModel as AssignmentActivityViewModel
        }
    }

    private fun saveState(viewPager: ViewPagerModified) {
        viewModel.mViewPagerState = viewPager.onSaveInstanceState()
    }

    fun bindViewModel(locaViewModel: AssignmentActivityViewModel) {
        viewModel = locaViewModel
        mBind?.viewModel = viewModel
    }

    private fun restoreState(viewPager: ViewPagerModified) {
        if (null != viewModel.mViewPagerState) {
            viewPager.onRestoreInstanceState(viewModel.mViewPagerState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(mStateKey, viewModel)
    }
}