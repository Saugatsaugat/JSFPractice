/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "BookingDetail")
public class BookingDetail extends AbstractEntity<BookingDetail> implements IAbstractEntity, Serializable {
    private String paymentstatus;
    
    @ManyToOne
    @JoinColumn(name = "futsalscheduleid")
    private FutsalSchedule futsalschedule;
    
    @ManyToOne
    @JoinColumn(name = "bookinginformationid")
    private BookingInformation bookinginformation;

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public FutsalSchedule getFutsalschedule() {
        return futsalschedule;
    }

    public void setFutsalschedule(FutsalSchedule futsalschedule) {
        this.futsalschedule = futsalschedule;
    }

    public BookingInformation getBookinginformation() {
        return bookinginformation;
    }

    public void setBookinginformation(BookingInformation bookinginformation) {
        this.bookinginformation = bookinginformation;
    }

    @Override
    public String getTableName() {
        return "BookingDetail";
    }
    
}
