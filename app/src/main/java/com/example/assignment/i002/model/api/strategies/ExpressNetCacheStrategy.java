package com.example.assignment.i002.model.api.strategies;

import com.example.assignment.helpers.LogProxy;
import com.example.assignment.helpers.network.GsonParser;
import com.example.assignment.helpers.network.ops.CacheStrategyOps;
import com.example.assignment.helpers.network.ops.RequestStrategyOps;
import com.example.assignment.helpers.preferences.PreferencesManager;
import com.example.assignment.helpers.strategies.CacheValidityType;
import com.example.assignment.i002.model.api.ExpressNetPreferences;
import com.example.assignment.i002.model.api.dto.SamplePojo;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import rx.Observable;

public class ExpressNetCacheStrategy<T> implements CacheStrategyOps<T,List<SamplePojo>> {

    private CacheValidityType mCacheStrategyType;
    final private int cacheTimeInSeconds = 10;
    final private String TAG=CacheStrategyOps.class.getSimpleName();

    @Override
    public Boolean isValid(RequestStrategyOps<T, List<SamplePojo>> mRequestStrategy) throws Exception {
        mRequestStrategy.checkParallelRequest();
        String result = PreferencesManager.getInstance().getValueAsString(ExpressNetPreferences.DATA);
        if (null == result) {
            mCacheStrategyType = CacheValidityType.INVALID;
        } else {
            Long expire = PreferencesManager.getInstance().getValueAsLong(ExpressNetPreferences.EXPIRED_DATE);
            if (expire <= System.currentTimeMillis()) {
                mCacheStrategyType = CacheValidityType.EXPIRED;
            } else {
                mCacheStrategyType = CacheValidityType.VALID;
            }
        }
        return mCacheStrategyType == CacheValidityType.VALID;
    }

    @Override
    public void invalidateCache() {
        PreferencesManager.getInstance().setStringValue(ExpressNetPreferences.DATA,null);
        mCacheStrategyType = CacheValidityType.INVALID;
    }

    @Override
    public CacheValidityType getCacheStrategyType() {
        return mCacheStrategyType;
    }

    @Override
    public CacheStrategyOps setCacheStrategyType(CacheValidityType cacheStrategyType) {
        this.mCacheStrategyType = cacheStrategyType;
        return this;
    }

    @Override
    public Observable<List<SamplePojo>> getData() {
        String json = PreferencesManager.getInstance().getValueAsString(ExpressNetPreferences.DATA);
        try {
            return Observable.just(GsonParser.getParsedObjectByType(json, new TypeToken<List<SamplePojo>>(){}));
        } catch (Exception e) {
            LogProxy.eTestFailedReport(TAG,e);
            return Observable.just(null);
        }
    }

    @Override
    public CacheStrategyOps<T, List<SamplePojo>> setData(List<SamplePojo> data) {
        PreferencesManager.getInstance().setStringValue(ExpressNetPreferences.DATA, GsonParser.toJson(data));
        PreferencesManager.getInstance().setLongValue(ExpressNetPreferences.EXPIRED_DATE, System.currentTimeMillis() + cacheTimeInSeconds*3600);
        return this;
    }

    @Override
    public void completeRequestAndStoreData(List<SamplePojo> data, RequestStrategyOps<T, List<SamplePojo>> requestStrategy) throws Exception {
        requestStrategy.completeParallelRequest();
        if (getCacheStrategyType() != CacheValidityType.VALID) {
            setData(data);
        }
    }

    @Override
    public void completeRequest(RequestStrategyOps<T, List<SamplePojo>> requestStrategy) {
        requestStrategy.completeParallelRequest();
    }
}