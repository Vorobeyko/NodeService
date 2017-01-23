package com.nodeservice.instance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by avorobey on 13.08.2016.
 * Сущность для операции с таблицей history
 */
@Entity
@Table(name = "history")
public class History implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "LastUpdated")
    private Date lastUpdated;
    @Column(name = "WhoUpdated")
    private String whoUpdated;
    @Column(name = "SourceIp")
    private String sourceIp;
    @Column(name = "SourceModel")
    private String sourceModel;
    @Column(name = "SourceDescription")
    private String sourceDescription;
    @Column(name = "OwnBy")
    private String ownBy;
    @Column(name = "Comments")
    private String comments;
    @Column(name = "DueData")
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

    public String getWhoUpdated() {
        return whoUpdated;
    }

    public void setWhoUpdated(String whoUpdated) {
        this.whoUpdated = whoUpdated;
    }
}
