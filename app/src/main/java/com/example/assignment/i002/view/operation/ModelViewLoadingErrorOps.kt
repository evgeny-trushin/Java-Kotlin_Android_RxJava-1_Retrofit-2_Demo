package com.example.assignment.i002.view.operation

import android.content.Context
import com.example.assignment.helpers.VisibilityViewModel

interface ModelViewLoadingErrorOps {
    var error:Boolean
    fun isErrorVisible() = VisibilityViewModel.visibleIfTrue(error)
    fun clickOnRetryLoadButton(context: Context)
}

