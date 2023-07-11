/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.saugat.beans;

import Entities.FutsalSchedule;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author saugat
 */
@Named
@SessionScoped
public class SlotScheduleBean implements Serializable {

    private FutsalSchedule futsalSchedule;
    private Date dateFrom;
    private Date dateTo;
    @Temporal(TemporalType.TIME)
    private Date breakTimeFrom;
    @Temporal(TemporalType.TIME)
    private Date breakTimeTo;

    public FutsalSchedule getFutsalSchedule() {
        return futsalSchedule;
    }

    public void setFutsalSchedule(FutsalSchedule futsalSchedule) {
        this.futsalSchedule = futsalSchedule;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getBreakTimeFrom() {
        return breakTimeFrom;
    }

    public void setBreakTimeFrom(Date breakTimeFrom) {
        this.breakTimeFrom = breakTimeFrom;
    }

    public Date getBreakTimeTo() {
        return breakTimeTo;
    }

    public void setBreakTimeTo(Date breakTimeTo) {
        this.breakTimeTo = breakTimeTo;
    }
    

}
