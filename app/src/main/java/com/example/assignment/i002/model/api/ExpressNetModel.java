package com.example.assignment.i002.model.api;

import com.example.assignment.helpers.ApiAbstractFactory;
import com.example.assignment.helpers.ops.CacheStrategyOps;
import com.example.assignment.helpers.ops.RequestStrategyOps;
import com.example.assignment.i002.model.api.dto.Pojo;
import com.example.assignment.i002.model.api.strategies.ExpressNetCacheStrategy;

import rx.exceptions.Exceptions;
import rx.subjects.ReplaySubject;


public class ExpressNetModel {

    public static ReplaySubject<Pojo> getData() throws Exception {
        ApiAbstractFactory<
            Void,
            Pojo,
            ExpressNetApiImpl,
            ExpressNetCacheStrategy<Pojo>,
            RequestStrategyOps>
            apiFactory = new ApiAbstractFactory<>();
        apiFactory.setApiStrategy(new ExpressNetApiImpl());
        apiFactory.setApiGetMethod(ExpressNetApiImpl::getData);
        final ReplaySubject<Pojo> subject = ReplaySubject.create();
//        apiFactory.getObservableDataFromApi()
//            .subscribe(response -> {
//                    try {
//                        apiFactory.getCacheStrategy().completeRequestAndStoreData(response, apiFactory.getRequestStrategy());
//                        subject.onCompleted();
//                    } catch (Exception e) {
//                        throw Exceptions.propagate(e);
//                    }
//                }, error -> {
//                    //ErrorsHandlingStrategy.handleError(error, subject)
//                }
//            );
        return subject;
    }
}
