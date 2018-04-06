package com.example.assignment.i002.model.api.strategies;

import com.example.assignment.helpers.ops.CacheStrategyOps;
import com.example.assignment.helpers.ops.RequestStrategyOps;
import com.example.assignment.helpers.strategies.CacheValidityType;
import com.example.assignment.i002.model.api.dto.Pojo;

import rx.Observable;

public class ExpressNetCacheStrategy<T> implements CacheStrategyOps<T,Pojo> {

    @Override
    public Boolean isValid(RequestStrategyOps<T, Pojo> mRequestStrategy) throws Exception {
        return false;
    }

    @Override
    public void invalidateCache() {

    }

    @Override
    public CacheValidityType getCacheStrategyType() {
        return null;
    }

    @Override
    public CacheStrategyOps setCacheStrategyType(CacheValidityType typeOfCache) {
        return null;
    }

    @Override
    public Observable<Pojo> getData() {
        return null;
    }

    @Override
    public CacheStrategyOps<T, Pojo> setData(Pojo data) {
        return null;
    }

    @Override
    public void completeRequestAndStoreData(Pojo data, RequestStrategyOps<T, Pojo> requestStrategy) throws Exception {

    }
}