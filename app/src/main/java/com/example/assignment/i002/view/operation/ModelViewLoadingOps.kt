package com.example.assignment.i002.view.operation

import com.example.assignment.helpers.VisibilityViewModel

interface ModelViewLoadingOps : ModelViewLoadingErrorOps {
    var isLoading:Boolean
    fun isContentVisible() = VisibilityViewModel.hiddenIfTrue(error || isLoading)
    fun isProgressBarVisible() = VisibilityViewModel.visibleIfTrue(!error && isLoading)
}
