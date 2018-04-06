package com.example.assignment.helpers.ops;

import com.example.assignment.helpers.exceptions.ParallelRequestException;

public interface RequestStrategyOps<REQUEST, RESPONSE> {
  boolean isAllowedToStart();
  void completeRequest();
  void completeParallelRequest();
  RequestStrategyOps<REQUEST, RESPONSE> setRequest(REQUEST request);
  RequestStrategyOps<REQUEST, RESPONSE> setToken(String token);
  REQUEST getRequest();
  String getToken();
  void checkParallelRequest() throws ParallelRequestException;
}
