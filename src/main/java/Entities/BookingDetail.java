/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "BookingDetail")
public class BookingDetail extends AbstractEntity implements IAbstractEntity, Serializable {
    private String paymentstatus;
    private Long futsalscheduleid;
    private Long bookinginformationid;

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public Long getFutsalscheduleid() {
        return futsalscheduleid;
    }

    public void setFutsalscheduleid(Long futsalscheduleid) {
        this.futsalscheduleid = futsalscheduleid;
    }

    public Long getBookinginformationid() {
        return bookinginformationid;
    }

    public void setBookinginformationid(Long bookinginformationid) {
        this.bookinginformationid = bookinginformationid;
    }
    
    

    @Override
    public String getTableName() {
        return "BookingDetail";
    }
    
}
