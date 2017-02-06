package com.nodeservice.instance;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class CustomerDateAndTimeDeserialize extends JsonDeserializer {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private final Logger _log = LogManager.getLogger(this.getClass());

    @Override
    public Date deserialize(JsonParser paramJsonParser,
                            DeserializationContext paramDeserializationContext)
            throws IOException, JsonProcessingException {
        String str = paramJsonParser.getValueAsString();
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            _log.error("Возникла проблема в парсинге даты");
        }
        return paramDeserializationContext.parseDate(str);
    }
}

@Entity
@Table(name = "sourceinfo")
public class Cameras implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SourceID")
    private Integer sourceId = 0;
    @Column(name = "SourceIp")
    private String sourceIp;
    @Column(name = "SourceModel")
    private String sourceModel;
    @Column(name = "SourceDescription")
    private String sourceDescription;
    @Column(name = "SourceType")
    private String sourceType;
    @Column(name = "OwnBy")
    private String ownBy;
    @Column(name = "Comments")
    private String comments;
    @Column(name = "DueData")
    @JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
    private Date dueData;
    @Column(name = "State")
    private String state = "free";
    @Column(name = "DeletedSource")
    private Boolean deletedSource = false;

    public Cameras(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Cameras(String sourceIp, String sourceModel, String sourceDescription, String sourceType, String ownBy, String comments, Date dueData, String state) {
        this.sourceIp = sourceIp;
        this.sourceModel = sourceModel;
        this.sourceDescription = sourceDescription;
        this.sourceType = sourceType;
        this.ownBy = ownBy;
        this.comments = comments;
        this.dueData = dueData;
        this.state = state;
    }

    public Cameras(String sourceIp, String sourceModel, String sourceDescription, String ownBy, String comments, String state) {
        this.sourceIp = sourceIp;
        this.sourceModel = sourceModel;
        this.sourceDescription = sourceDescription;
        this.ownBy = ownBy;
        this.comments = comments;

        this.state = state;
    }

    public Cameras() {
    }

    public Integer getSourceId (){
        return sourceId;
    }

    public String getSourceIp () {
        return sourceIp;
    }

    public String getSourceModel (){
        return sourceModel;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getOwnBy() {
        return ownBy;
    }

    public String getComments() {
        if (comments == null || comments.isEmpty()){
            return " ";
        } else {
            return comments;
        }

    }

    public Date getDueData() {
        return dueData;
    }

    public String getState() {
        return state;
    }

    public Boolean getDeletedSource() {
        return deletedSource;
    }

    public void setSourceId (Integer sourceId){ this.sourceId = sourceId; }

    public void setSourceIp (String source_ip){ this.sourceIp = source_ip; }

    public void setSourceModel (String source_model){ this.sourceModel = source_model;}

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public void setOwnBy(String ownBy) {
        this.ownBy = ownBy;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDueData(Date dueData) {
        this.dueData = dueData;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDeletedSource(Boolean deletedSource) {
        this.deletedSource = deletedSource;
    }
}
