package com.revature.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "parsed_data")
public class ParsedData {

    @Id
    private String id;
    private String metadataId;
    private Object parsedData;

    public ParsedData() {
    }

    public ParsedData(Object parsedData) {
        this.parsedData = parsedData;
    }
    public ParsedData(String metadataId, Object parsedData){
        this.metadataId = metadataId;
        this.parsedData = parsedData;
    }

    public String getId() {
        return id;
    }


    public Object getParsedData() {
        return parsedData;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    @Override
    public String toString() {
        return "ParsedData{" +
                "id='" + id + '\'' +
                ", metadataId='" + metadataId + '\'' +
                ", parsedData=" + parsedData +
                '}';
    }
}
