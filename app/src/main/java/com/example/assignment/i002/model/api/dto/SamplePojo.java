package com.example.assignment.i002.model.api.dto;

import java.io.Serializable;

public class SamplePojo implements Serializable {
    private String id;

    private Location location;

    private String name;

    private Fromcentral fromcentral;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fromcentral getFromcentral() {
        return fromcentral;
    }

    public void setFromcentral(Fromcentral fromcentral) {
        this.fromcentral = fromcentral;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", location = " + location + ", name = " + name + ", fromcentral = " + fromcentral + "]";
    }
}