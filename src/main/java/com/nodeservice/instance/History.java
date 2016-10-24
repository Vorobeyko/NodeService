package com.nodeservice.instance;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by avorobey on 13.08.2016.
 * Сущность для операции с таблицей history
 */

public class History implements Serializable {
    private Integer ID;
    private Date lastUpdated;
    private String sourceIp;
    private String sourceModel;
    private String sourceDescription;
    private String ownBy;
    private String comments;
    private Date dueData;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setLastUpdated(Date lastUpdated){
        this.lastUpdated = lastUpdated;
    }

    public Date getLastUpdated(){
        return lastUpdated;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceModel(String sourceModel) {
        this.sourceModel = sourceModel;
    }

    public String getSourceModel() {
        return sourceModel;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setOwnBy(String ownBy) {
        this.ownBy = ownBy;
    }

    public String getOwnBy() {
        return ownBy;
    }

    public void setDueData(Date dueData) {
        this.dueData = dueData;
    }

    public Date getDueData() {
        return dueData;
    }
}
