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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author saugat
 */

@Entity
@Table(name = "BookingInformation")
public class BookingInformation extends AbstractEntity<BookingInformation> implements IAbstractEntity, Serializable{
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date entrydate;
    private float amount;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fromdate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date todate;
    
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    
    @Override
    public String getTableName() {
        return "BookingInformation";
    }
    
}
