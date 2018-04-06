package com.example.assignment.i002.model.api;

import com.example.assignment.i002.model.api.dto.SamplePojo;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface ExpressNetApiOps {
    @GET("/sample.json")
    Observable<List<SamplePojo>> getData();
}