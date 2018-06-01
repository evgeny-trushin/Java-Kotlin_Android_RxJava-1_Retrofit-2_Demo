package com.example.assignment.i002.view.operation

import android.content.Context
import com.example.assignment.helpers.ErrorsHandlerKotlin
import com.example.assignment.helpers.LogProxy
import com.example.assignment.helpers.VisibilityViewModel
import com.example.assignment.helpers.exceptions.ParallelRequestException
import com.example.assignment.helpers.exceptions.ServerRequestException
import com.example.assignment.helpers.network.ErrorCode
import retrofit2.adapter.rxjava.HttpException
import java.io.IOException
import java.net.*

interface ModelViewLoadingErrorOps {
    var error: Boolean
    var errorCode: ErrorCode
    fun isErrorVisible() = VisibilityViewModel.visibleIfTrue(error)
    fun clickOnRetryLoadButton(context: Context)
    fun setExceptionAsErrorCode(e: Throwable) {
        errorCode = when (e) {
            is HttpException -> ErrorCode.ERROR_HOST_FAULT
            is ParallelRequestException -> ErrorCode.ERROR_IGNORE
            is SocketTimeoutException -> ErrorCode.ERROR_HOST_TIMEOUT
            is IOException,
            is UnknownHostException,
            is UnknownServiceException,
            is ConnectException,
            is SocketException,
            is ProtocolException -> ErrorCode.ERROR_HOST_UNAVAILABLE
            else -> ErrorCode.ERROR_INTERNAL
        }

        error = errorCode != ErrorCode.ERROR_IGNORE
        LogProxy.eTestFailedReport(ErrorsHandlerKotlin.TAG, ServerRequestException(
                "Error " + e.message + "\n" +
                        "ErrorType "))
    }
}