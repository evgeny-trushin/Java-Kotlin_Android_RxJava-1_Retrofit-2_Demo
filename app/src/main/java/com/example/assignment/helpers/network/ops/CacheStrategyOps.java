package com.example.assignment.helpers.network.ops;

import com.example.assignment.helpers.strategies.CacheValidityType;
import com.example.assignment.i002.model.api.dto.SamplePojo;

import java.util.List;

import rx.Observable;

public interface CacheStrategyOps<REQUEST, RESPONSE> {
  Boolean isValid(RequestStrategyOps<REQUEST,RESPONSE> mRequestStrategy) throws Exception;
  void invalidateCache();
  CacheValidityType getCacheStrategyType();
  CacheStrategyOps setCacheStrategyType(CacheValidityType typeOfCache);
  Observable<RESPONSE> getData();
  CacheStrategyOps<REQUEST, RESPONSE> setData(RESPONSE data);
  void completeRequestAndStoreData(RESPONSE data, RequestStrategyOps<REQUEST,RESPONSE> requestStrategy) throws Exception;
  void completeRequest(RequestStrategyOps<REQUEST,RESPONSE> requestStrategy);
}
