package com.example.assignment.helpers.network;

import com.example.assignment.helpers.LogProxy;
import com.example.assignment.helpers.exceptions.ServerRequestException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.adapter.rxjava.HttpException;
import rx.subjects.ReplaySubject;

public class ErrorHandler<ResponseType> {

    enum NetworkErrors {
        HttpException,
        IOException,
        ParallelRequestException
    }
    private static String TAG = ErrorHandler.class.getSimpleName();

    @SuppressWarnings("unchecked")
    static public <T> void handleError(Throwable error, ReplaySubject<T> subject) {
        NetworkErrors enumError;
        try {
            enumError = NetworkErrors.valueOf(error.getClass().getName());
            LogProxy.eTestFailedReport(TAG,"enumError: " + enumError);
        } catch (Exception e) {
            LogProxy.eTestFailedReport(TAG,e);
        }
        if (error instanceof HttpException) {
//            (HttpException) ((HttpException) error).code()
            subject.onCompleted();
        } else if (error instanceof IOException) {
            if (error instanceof SocketTimeoutException ||
                error instanceof UnknownHostException ||
                error instanceof UnknownServiceException ||
                error instanceof ConnectException ||
                error instanceof SocketException ||
                error instanceof ProtocolException
                ) {
//                ErrorCode.ERROR_NETWORK_INTERNET
            } else {
//                ErrorCode.ERROR_EXTERNAL
            }
        }
        LogProxy.eTestFailedReport(TAG, new ServerRequestException(
            "Error " + error.getMessage() + "\n" +
                "ErrorType "));
    }
}
