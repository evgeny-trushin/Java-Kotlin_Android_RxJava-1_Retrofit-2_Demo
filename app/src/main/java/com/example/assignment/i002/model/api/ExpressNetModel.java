package com.example.assignment.i002.model.api;

import com.example.assignment.helpers.ErrorsHandlerKotlin;
import com.example.assignment.helpers.network.ApiAbstractFactory;
import com.example.assignment.helpers.network.ErrorHandler;
import com.example.assignment.i002.model.api.dto.SamplePojo;
import com.example.assignment.i002.model.api.strategies.ExpressNetCacheStrategy;
import com.example.assignment.i002.model.api.strategies.ExpressNetRequestStrategy;

import java.util.List;

import rx.exceptions.Exceptions;
import rx.subjects.ReplaySubject;


public class ExpressNetModel {

    private static final String TAG = ExpressNetModel.class.getSimpleName();

    public static ReplaySubject<List<SamplePojo>> getData() throws Exception {
        ApiAbstractFactory<
            Void,
            List<SamplePojo>,
            ExpressNetApiImpl,
            ExpressNetCacheStrategy<List<SamplePojo>>,
            ExpressNetRequestStrategy>
            apiFactory = new ApiAbstractFactory<>();
        apiFactory.setApiStrategy(new ExpressNetApiImpl());
        apiFactory.setRequestStrategy(new ExpressNetRequestStrategy<>());
        apiFactory.setCacheStrategy(new ExpressNetCacheStrategy<>());
        apiFactory.setApiGetMethod(ExpressNetApiImpl::getData);
        final ReplaySubject<List<SamplePojo>> subject = ReplaySubject.create();
        try {
            apiFactory.getObservableDataFromApi()
                .subscribe(data -> {
                        try {
                            apiFactory.getCacheStrategy().completeRequestAndStoreData(data, apiFactory.getRequestStrategy());
                            subject.onNext(data);
                            subject.onCompleted();
                        } catch (Exception e) {
                            throw Exceptions.propagate(e);
                        }
                    }, error -> ErrorsHandlerKotlin.Companion.handleError(error, subject)
                );
        } catch (Exception e) {
            ErrorsHandlerKotlin.Companion.handleError(e, subject);
            apiFactory.getCacheStrategy().completeRequest(apiFactory.getRequestStrategy());
        }
        return subject;
    }
}
