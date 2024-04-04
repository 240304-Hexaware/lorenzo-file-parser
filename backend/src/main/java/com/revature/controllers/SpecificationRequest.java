package com.revature.controllers;

public class SpecificationRequest {
    private String userId;
//    private String specificationId;
    private String specificationName;
    private String path;

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationId(String specificationName) {
        this.specificationName = specificationName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
