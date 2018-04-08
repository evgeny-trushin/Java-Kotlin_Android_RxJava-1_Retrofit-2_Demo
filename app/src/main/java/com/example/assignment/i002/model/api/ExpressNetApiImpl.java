package com.example.assignment.i002.model.api;

import com.example.assignment.helpers.network.ApiService;
import com.example.assignment.helpers.PoolManager;
import com.example.assignment.i002.model.api.dto.SamplePojo;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ExpressNetApiImpl implements ExpressNetApiOps {

    private static ExpressNetApiOps mService = ApiService.createService(ExpressNetApiOps.class);

    @Override
    public Observable<List<SamplePojo>> getData() {
        return mService.getData().
            subscribeOn(Schedulers.from(PoolManager.getInstance().getThreadPoolExecutor()))
            .unsubscribeOn(Schedulers.from(PoolManager.getInstance().getThreadPoolExecutor()));
    }
}