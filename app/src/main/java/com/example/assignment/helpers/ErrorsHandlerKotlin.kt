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

    companion object {
        val TAG = ErrorsHandlerKotlin::class.java.simpleName!!
        fun <T> handleError(error: Throwable, subject: ReplaySubject<T>) {

        }
    }
}
