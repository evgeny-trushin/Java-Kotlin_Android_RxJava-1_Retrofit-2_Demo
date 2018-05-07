package com.example.assignment.i002.view.operation

import android.content.Context

interface ModelViewError {
    var error:Boolean
    fun isContentVisible():Int
    fun isErrorVisible():Int
    fun clickOnRetryButton(context: Context)
}

