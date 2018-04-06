package com.example.assignment.i002.model.api;

import com.example.assignment.helpers.ApiService;
import com.example.assignment.helpers.PoolManager;
import com.example.assignment.i002.model.api.dto.Pojo;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ExpressNetApiImpl implements ExpressNetApiOps {

    private String TAG = this.getClass().getSimpleName();
    private static ExpressNetApiOps mService = ApiService.createService(ExpressNetApiOps.class);

    @Override
    public Observable<Pojo> getData() {
        return mService.getData().
            subscribeOn(Schedulers.from(PoolManager.getInstance().getThreadPoolExecutor()))
            .unsubscribeOn(Schedulers.from(PoolManager.getInstance().getThreadPoolExecutor()));
    }
}