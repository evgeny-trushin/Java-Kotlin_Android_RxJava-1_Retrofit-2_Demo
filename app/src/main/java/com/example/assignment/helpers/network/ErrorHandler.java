package com.example.assignment.helpers.network;

import com.example.assignment.helpers.CrashlyticsProxy;
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

    private static String TAG = ErrorHandler.class.getSimpleName();

    @SuppressWarnings("unchecked")
    static public <T> void handleError(Throwable error, ReplaySubject<T> subject) {
        if (error instanceof HttpException) {
            subject.onCompleted();
        } else if (error instanceof IOException) {
            if (error instanceof SocketTimeoutException ||
                error instanceof UnknownHostException ||
                error instanceof UnknownServiceException ||
                error instanceof ConnectException ||
                error instanceof SocketException ||
                error instanceof ProtocolException
                ) {
//                ErrorCodeMessages.ERROR_NETWORK_INTERNET
            } else {
//                ErrorCodeMessages.ERROR_EXTERNAL
            }
        }
        CrashlyticsProxy.eTestFailedReport(TAG, new ServerRequestException(
            "Error " + error.getMessage() + "\n" +
                "ErrorType "));
    }
}
