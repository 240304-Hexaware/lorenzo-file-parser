package com.revature.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Document(collection = "specifications")
public class Specification {
    @Id
    private String id;
    private String userId;
    private String specName;
    private Map<String, Field> specs;

    public Specification() {
    }

    public Specification(String specName, Map<String, Field> specs){
        this.specName = specName;
        this.specs = specs;
    }

    public Specification(String id, String userId, String specName, Map<String, Field> specs){
        this.id = id;
        this.userId = userId;
        this.specName = specName;
        this.specs = specs;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getSpecName() {
        return specName;
    }

    public Map<String, Field> getSpecs() {
        return specs;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}