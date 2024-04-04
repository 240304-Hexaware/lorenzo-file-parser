package com.revature.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class Field {
    private Integer startPos;
    private Integer endPos;
    private String dataType;

    public Field() {
    }

    public Field(Integer startPos, Integer endPos, String dataType) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.dataType = dataType;
    }



    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public void setEndPos(Integer endPos) {
        this.endPos = endPos;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Token{" +
                ", startPos=" + startPos +
                ", endPos=" + endPos +
                ", dataType=" + dataType +
                '}';
    }
}