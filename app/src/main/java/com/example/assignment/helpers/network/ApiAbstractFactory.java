package com.example.assignment.helpers.network;

import com.example.assignment.helpers.CrashlyticsProxy;
import com.example.assignment.helpers.network.ops.CacheStrategyOps;
import com.example.assignment.helpers.network.ops.DeleteStrategyOps;
import com.example.assignment.helpers.network.ops.RequestStrategyOps;
import com.example.assignment.helpers.strategies.CacheValidityType;

import rx.Observable;

public class ApiAbstractFactory<REQUEST, RESPONSE, API, CACHE extends CacheStrategyOps, REQUEST_STRATEGY extends RequestStrategyOps> {

    final private String TAG = getClass().getSimpleName();
    private Observable<RESPONSE> mObservableData = null;
    private API mApiStrategy = null;
    private RequestStrategyOps<REQUEST, RESPONSE> mRequestStrategy;
    private CacheStrategyOps<REQUEST, RESPONSE> mCacheStrategy = null;
    private DeleteStrategyOps mDeleteStrategy = null;
    private boolean mStopEvent = false;

    public interface GetObservablePostData<REQUEST, RESPONSE, API> {
        Observable<RESPONSE> getObservableData(API api, REQUEST request);
    }

    public interface GetObservablePostDataWithToken<REQUEST, RESPONSE, API> {
        Observable<RESPONSE> getObservableData(API api, REQUEST request, String token);
    }

    public interface GetObservableGetData<RESPONSE, API> {
        Observable<RESPONSE> getObservableData(API api);
    }

    public interface GetObservableDataWithToken<RESPONSE, API> {
        Observable<RESPONSE> getObservableData(API api, String token);
    }

    public ApiAbstractFactory<REQUEST, RESPONSE, API, CACHE, REQUEST_STRATEGY> setApiGetMethod(GetObservableGetData<RESPONSE, API> observableData) throws Exception {
        if (null != mRequestStrategy && null != mApiStrategy) {
            this.mObservableData = observableData.getObservableData(mApiStrategy);
        } else {
            throw new Exception("RequestStrategy is NULL");
        }
        return this;
    }

    public ApiAbstractFactory<REQUEST, RESPONSE, API, CACHE, REQUEST_STRATEGY> setApiGetMethodWithToken(GetObservableDataWithToken<RESPONSE, API> observableData, String token) throws Exception {
        if (null != mRequestStrategy) {
            this.mObservableData = observableData.getObservableData(mApiStrategy, token);
        } else {
            throw new Exception("RequestStrategy is NULL");
        }
        return this;
    }

    public ApiAbstractFactory<REQUEST, RESPONSE, API, CACHE, REQUEST_STRATEGY> setApiPostMethod(GetObservablePostData<REQUEST, RESPONSE, API> observableData) throws Exception {
        if (null != mRequestStrategy) {
            this.mObservableData = observableData.getObservableData(mApiStrategy, getRequestStrategy().getRequest());
        } else {
            throw new Exception("RequestStrategy is NULL");
        }
        return this;
    }

    public void invalidateCache() {
        try {
            if (null != mCacheStrategy) {
                mCacheStrategy.invalidateCache();
            }
        } catch (Exception e) {
            CrashlyticsProxy.e(TAG, e);
        }
    }

    public void completeParallelRequest() {
        try {
            if (null != mRequestStrategy) {
                mRequestStrategy.completeParallelRequest();
            }
        } catch (Exception e) {
//            CrashlyticsProxy.e(TAG, e);
        }
    }

    protected void completeParallelRequestIfCacheIsInvalid() {
        try {
            if (null != mCacheStrategy && CacheValidityType.INVALID == mCacheStrategy.getCacheStrategyType()) {
                mRequestStrategy.completeParallelRequest();
            }
        } catch (Exception e) {
            CrashlyticsProxy.e(TAG, e);
        }
    }

    public boolean isEventEventWasStopped() {
        return mStopEvent;
    }

    public void setStopEvent(boolean mStopEvent) {
        this.mStopEvent = mStopEvent;
    }

    public API getApiStrategy() {
        return mApiStrategy;
    }

    public DeleteStrategyOps getDeleteStrategy() {
        return mDeleteStrategy;
    }

    public void setDeleteStrategy(DeleteStrategyOps mDeleteStrategy) {
        this.mDeleteStrategy = mDeleteStrategy;
    }


    @SuppressWarnings("unchecked")
    public ApiAbstractFactory<REQUEST, RESPONSE, API, CACHE, REQUEST_STRATEGY> setApiStrategy(API mApi) {
        this.mApiStrategy = mApi;
        return this;
    }

    @SuppressWarnings("unchecked")
    public ApiAbstractFactory<REQUEST, RESPONSE, API, CACHE, REQUEST_STRATEGY> setCacheStrategy(CacheStrategyOps<REQUEST, RESPONSE> mCacheStrategy) {
        this.mCacheStrategy = mCacheStrategy;
        return this;
    }

    @SuppressWarnings("unchecked")
    public ApiAbstractFactory<REQUEST, RESPONSE, API, CACHE, REQUEST_STRATEGY> setRequestStrategy(RequestStrategyOps<REQUEST, RESPONSE> mRequestStrategy) {
        this.mRequestStrategy = mRequestStrategy;
        return this;
    }

    public RequestStrategyOps<REQUEST, RESPONSE> getRequestStrategy() {
        return mRequestStrategy;
    }

    private void catchError(String tag, Exception e) {
        CrashlyticsProxy.e(TAG, e);
    }

    public void catchError(String tag, Throwable e) {
        CrashlyticsProxy.e(TAG, e);
    }

    public CacheStrategyOps<REQUEST, RESPONSE> getCacheStrategy() {
        return mCacheStrategy;
    }

    @SuppressWarnings("unchecked")
    public Observable<RESPONSE> getObservableDataFromApi() throws Exception {
        try {
            if (null!=getCacheStrategy()&&getCacheStrategy().isValid(mRequestStrategy)) {
                return getCacheStrategy().getData();
            } else {
                return mObservableData;
            }

        } catch (Exception e) {
            catchError(TAG, e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public Observable<RESPONSE> getObservableDataFromApiWithoutCache() {
        return mObservableData;
    }
}