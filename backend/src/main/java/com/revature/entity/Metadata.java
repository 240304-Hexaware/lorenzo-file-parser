package com.revature.entity;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "metadata")
public class Metadata {
    @Id
    private String id;
    private String userId;
    private String parsedDataId;
    private String flatFilePath;
    private Date uploadDate;
    private String specName;

    public Metadata() {
    }

    public Metadata(String userId, String flatFilePath, String specName) {
        this.userId = userId;
        this.flatFilePath = flatFilePath;
        this.specName = specName;
        this.uploadDate = new Date();
    }

    public Metadata(String id, String userId, String flatFilePath, String specName, String parsedDataId) {
        this.id = id;
        this.userId = userId;
        this.flatFilePath = flatFilePath;
        this.uploadDate = new Date();
        this.specName = specName;
        this.parsedDataId = parsedDataId;
    }

    public Metadata(String userId, String flatFilePath, String specName, String parsedDataId) {
        this.userId = userId;
        this.flatFilePath = flatFilePath;
        this.uploadDate = new Date();
        this.specName = specName;
        this.parsedDataId = parsedDataId;
    }

    public Metadata(String id, String userId, String flatFilePath, Date uploadDate, String specName, String parsedDataId) {
        this.id = id;
        this.userId = userId;
        this.flatFilePath = flatFilePath;
        this.uploadDate = uploadDate;
        this.specName = specName;
        this.parsedDataId = parsedDataId;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getFlatFilePath() {
        return flatFilePath;
    }

    public String getParsedDataId() {
        return parsedDataId;
    }

    public String getSpecName() {
        return specName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }
}
