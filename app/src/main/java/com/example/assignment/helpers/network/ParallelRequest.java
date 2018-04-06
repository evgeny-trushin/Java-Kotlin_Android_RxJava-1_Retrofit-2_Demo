package com.example.assignment.helpers.network;

import com.example.assignment.helpers.CrashlyticsProxy;
import com.example.assignment.helpers.exceptions.ParallelRequestException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The solution for exclusion duplicated requests from the queue.
 */
public class ParallelRequest {
    private static final int MAX_NUMBER_OF_PARALLEL_REQUEST_TO_API = 1;
    private static volatile ParallelRequest mInstance;
    private final ConcurrentHashMap<String, Long> mParallelRequests = new ConcurrentHashMap<>();
    private final int PARALLEL_REQUEST_TIMEOUT = 15;
    private String TAG = ParallelRequest.class.getSimpleName();

    private ParallelRequest() {
    }

    /**
     * Resets the queue
     */
    public void resetQueue() {
        mParallelRequests.clear();
    }


    /**
     * Returns the singleton
     *
     * @return itself
     */
    public static ParallelRequest getInstance() {
        if (null == mInstance) {
            synchronized (ParallelRequest.class) {
                if (null == mInstance) {
                    mInstance = new ParallelRequest();
                }
            }
        }
        return mInstance;
    }

    private synchronized boolean checkTheSameRequestAndSetFlag(String hash, long requestTimeout) {
        long currentTime = System.currentTimeMillis();
        try {
            if (mParallelRequests.containsKey(hash)) {
                CrashlyticsProxy.e(TAG, "containKey: Yes" + hash);
                Long timeOutReached = mParallelRequests.get(hash);
                if (timeOutReached <= currentTime) {
                    mParallelRequests.put(hash, currentTime + requestTimeout * 1000);
                    if (mParallelRequests.size() > MAX_NUMBER_OF_PARALLEL_REQUEST_TO_API) {
                        for (ConcurrentMap.Entry<String, Long> entry : mParallelRequests.entrySet()) {
                            if (entry.getValue() <= currentTime) {
                                try {
                                    mParallelRequests.remove(entry.getKey());
                                } catch (Exception e) {
                                    CrashlyticsProxy.d(TAG, e);
                                }
                            }
                        }
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                CrashlyticsProxy.e(TAG, "containKey: NO: " + hash);
                mParallelRequests.put(hash, currentTime + requestTimeout * 1000);
                return false;
            }
        } catch (Exception e) {
            CrashlyticsProxy.d(TAG, e);
            mParallelRequests.put(hash, currentTime + requestTimeout * 1000);
            return false;
        }
    }

    /**
     * Removes the request for the queue
     * @param hash of the object
     */
    public synchronized void completeParallelRequest(String hash) {
        try {
            mParallelRequests.remove(hash);
        } catch (Exception e) {
            CrashlyticsProxy.d("completeParallelRequest", e);
        }
    }

    /**
     * Checks the presence of a request in the queue
     * @param hash of the object
     * @throws ParallelRequestException - If there is the same request in the queue, it throws exception
     */
    public void checkParallelRequest(String hash) throws ParallelRequestException {
        if (checkTheSameRequestAndSetFlag(hash, PARALLEL_REQUEST_TIMEOUT)) {
            throw new ParallelRequestException("There is the same request");
        }
    }
}