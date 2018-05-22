package com.example.assignment.helpers

import com.example.assignment.helpers.exceptions.ParallelRequestException
import com.example.assignment.helpers.exceptions.ServerRequestException
import com.example.assignment.helpers.network.ErrorCode

import java.io.IOException
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

import retrofit2.adapter.rxjava.HttpException
import rx.subjects.ReplaySubject

class ErrorsHandlerKotlin {
    internal val TAG = ErrorsHandlerKotlin::class.java.simpleName

     companion object Test {
        fun <T> handleError(error: Throwable, subject: ReplaySubject<T>) {
//            val result: ErrorCode = when (error) {
//                    is HttpException -> ErrorCode.ERROR_HOST_USERS_FAULT
//                    is ParallelRequestException -> ErrorCode.ERROR_IGNORE
//                    is SocketTimeoutException -> ErrorCode.ERROR_HOST_TIMEOUT
//                    is IOException,
//                    is UnknownHostException,
//                    is UnknownServiceException,
//                    is ConnectException,
//                    is SocketException,
//                    is ProtocolException -> ErrorCode.ERROR_HOST_UNAVAILABLE
//                    else -> ErrorCode.ERROR_APP_INTERNAL
//                }
-
                        LogProxy.eTestFailedReport(TAG, ServerRequestException(
                    "Error " + error.message + "\n" +
                            "ErrorType "))

        }
        }
    }
}
