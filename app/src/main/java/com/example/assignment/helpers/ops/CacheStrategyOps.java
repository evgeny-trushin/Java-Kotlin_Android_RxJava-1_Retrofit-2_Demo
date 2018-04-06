package com.example.assignment.helpers.ops;

import com.example.assignment.helpers.strategies.CacheValidityType;

import rx.Observable;

public interface CacheStrategyOps<REQUEST, RESPONSE> {
  Boolean isValid(RequestStrategyOps<REQUEST,RESPONSE> mRequestStrategy) throws Exception;
  void invalidateCache();
  CacheValidityType getCacheStrategyType();
  CacheStrategyOps setCacheStrategyType(CacheValidityType typeOfCache);
  Observable<RESPONSE> getData();
  CacheStrategyOps<REQUEST, RESPONSE> setData(RESPONSE data);
  void completeRequestAndStoreData(RESPONSE data, RequestStrategyOps<REQUEST,RESPONSE> requestStrategy) throws Exception;
}
