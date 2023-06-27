/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name="FutsalSchedule")
public class FutsalSchedule extends AbstractEntity implements IAbstractEntity,Serializable{
    private int starthour;
    private int endhour;
    private Date scheduledate; 
    private String status;
    private Long userid;
    private float rate;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getStarthour() {
        return starthour;
    }

    public void setStarthour(int starthour) {
        this.starthour = starthour;
    }

    public int getEndhour() {
        return endhour;
    }

    public void setEndhour(int endhour) {
        this.endhour = endhour;
    }

    public Date getScheduledate() {
        return scheduledate;
    }

    public void setScheduledate(Date scheduledate) {
        this.scheduledate = scheduledate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Override
    public String getTableName() {
        return "FutsalSchedule";
    }
    
  
    
}
