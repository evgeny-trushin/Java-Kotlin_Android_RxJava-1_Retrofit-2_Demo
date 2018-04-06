package com.example.assignment.i002.model.api.strategies;

import com.example.assignment.helpers.GenericSingleton;
import com.example.assignment.helpers.network.ParallelRequest;
import com.example.assignment.helpers.exceptions.ParallelRequestException;
import com.example.assignment.helpers.network.ops.RequestStrategyOps;
import com.example.assignment.helpers.strategies.TimeoutStrategy;
import com.example.assignment.i002.model.api.dto.SamplePojo;

import java.util.List;

public class ExpressNetRequestStrategy<REQUEST> implements RequestStrategyOps<REQUEST, List<SamplePojo>> {
    private ParallelRequest mParallelRequest = ParallelRequest.getInstance();
    private String TAG = ExpressNetRequestStrategy.class.getSimpleName();
    private final TimeoutStrategy requestTimeout = (TimeoutStrategy) GenericSingleton.instance(
        TimeoutStrategy.class, new TimeoutStrategy(10)
    );

    @Override
    public boolean isAllowedToStart() {
        return requestTimeout.ifAllowedStartTheProcess();
    }

    @Override
    public void completeRequest() {
        requestTimeout.setStop();
    }

    @Override
    public void completeParallelRequest() {
        mParallelRequest.completeParallelRequest(TAG);
    }

    @Override
    public RequestStrategyOps<REQUEST, List<SamplePojo>> setRequest(REQUEST request) {
        return null;
    }

    @Override
    public REQUEST getRequest() {
        return null;
    }

    @Override
    public void checkParallelRequest() throws ParallelRequestException {
        mParallelRequest.checkParallelRequest(TAG);
    }
}