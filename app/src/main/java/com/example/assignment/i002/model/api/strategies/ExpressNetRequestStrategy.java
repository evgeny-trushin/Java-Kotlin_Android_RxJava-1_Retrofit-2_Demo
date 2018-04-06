package com.example.assignment.i002.model.api.strategies;

import com.example.assignment.helpers.exceptions.ParallelRequestException;
import com.example.assignment.helpers.ops.RequestStrategyOps;
import com.example.assignment.i002.model.api.dto.Pojo;

public class ExpressNetRequestStrategy<REQUEST> implements RequestStrategyOps<REQUEST, Pojo> {
    private REQUEST request;
    private String TAG = ExpressNetRequestStrategy.class.getSimpleName();

    @Override
    public boolean isAllowedToStart() {
        return false;
    }

    @Override
    public void completeRequest() {

    }

    @Override
    public void completeParallelRequest() {

    }

    @Override
    public RequestStrategyOps<REQUEST, Pojo> setRequest(REQUEST request) {
        return null;
    }

    @Override
    public RequestStrategyOps<REQUEST, Pojo> setToken(String token) {
        return null;
    }

    @Override
    public REQUEST getRequest() {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void checkParallelRequest() throws ParallelRequestException {

    }
}