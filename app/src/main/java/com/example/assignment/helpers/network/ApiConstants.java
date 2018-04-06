package com.example.assignment.helpers.network;

/**
 * A container of constants for API usage
 */
public class ApiConstants {

    private final static ApiConstants mInstance = new ApiConstants();
    private static String mUrl = "http://express-it.optusnet.com.au";

    /**
     * Returns singleton
     */
    public static ApiConstants getInstance() {
        return mInstance;
    }

    /**
     * Returns current the url of API
     */
    public String getUrl() {
        synchronized (mInstance) {
            return mUrl;
        }
    }

    /**
     * Sets the current url
     */
    public ApiConstants setUrl(String url) {
        synchronized (mInstance) {
            mUrl = url;
        }
        return getInstance();
    }
}