package com.nodeservice.instance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by VorobeyAlex on 02.11.2016.
 */
@Entity
@Table(name = "computers")
public class Computers {
    @Id
    @Column(name = "ComputerID")
    private Integer computerID;
    @Column(name = "ComputerIP")
    private String computerIP;
    @Column(name = "ComputerName")
    private String computerName;
    @Column(name = "ComputerDescription")
    private String computerDescription;

    public Computers (String compIP, String compName, String compDescription){
        this.computerIP = compIP;
        this.computerName = compName;
        this.computerDescription = compDescription;
    }

    public Computers(){}

    public Integer getComputerID() {
        return computerID;
    }

    public void setComputerID(Integer computerID) {
        this.computerID = computerID;
    }

    public String getComputerIP() {
        return computerIP;
    }

    public void setComputerIP(String computerIP) {
        this.computerIP = computerIP;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerDescription() {
        return computerDescription;
    }

    public void setComputerDescription(String computerDescription) {
        this.computerDescription = computerDescription;
    }
}
