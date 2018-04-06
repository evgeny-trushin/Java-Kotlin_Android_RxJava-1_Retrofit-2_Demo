package com.example.assignment.i002.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.assignment.i002.databinding.ActivityAssignmentBinding
import me.relex.circleindicator.CircleIndicator
import android.support.v4.view.ViewPager
import android.view.View
import com.example.assignment.helpers.ViewPagerModified
import com.example.assignment.i002.R
import com.example.assignment.i002.view.model.AssignmentActivityViewModel


class AssignmentActivity : AppCompatActivity() {
    private var mBind: ActivityAssignmentBinding? = null
    var viewModel = AssignmentActivityViewModel(
            "Test", arrayListOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5"), supportFragmentManager,
            R.color.colorWhite, 4
    )
    private val mStateKey = "STATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_assignment)
        restoreViewModelState(savedInstanceState)
        viewModel.manager = supportFragmentManager
        mBind?.viewModel = viewModel
        mBind?.buttons?.viewModel = viewModel
        ((mBind?.root as View).findViewById(R.id.pager) as ViewPager).addOnAdapterChangeListener { pager, _, _ ->
            run {
                ((mBind?.root as View).findViewById(R.id.indicator) as CircleIndicator).setViewPager(pager)
                restoreState(pager as ViewPagerModified)
            }
        }
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
        val viewPager = (mBind?.root as View).findViewById(R.id.pager) as ViewPagerModified
        saveState(viewPager)
        mBind?.viewModel = viewModel
    }

    private fun restoreState(viewPager: ViewPagerModified) {
        if (null != viewModel.mViewPagerState) {
            viewPager.onRestoreInstanceState(viewModel.mViewPagerState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val viewPager = (mBind?.root as View).findViewById(R.id.pager) as ViewPagerModified
        saveState(viewPager)
        outState?.putSerializable(mStateKey, viewModel)
    }
}