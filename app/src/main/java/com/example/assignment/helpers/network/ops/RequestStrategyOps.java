package com.example.assignment.helpers.network.ops;

import com.example.assignment.helpers.exceptions.ParallelRequestException;

public interface RequestStrategyOps<REQUEST, RESPONSE> {
  boolean isAllowedToStart();
  void completeRequest();
  void completeParallelRequest();
  RequestStrategyOps<REQUEST, RESPONSE> setRequest(REQUEST request);
  REQUEST getRequest();
  void checkParallelRequest() throws ParallelRequestException;
}
