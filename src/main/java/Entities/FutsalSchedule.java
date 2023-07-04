
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
@Table(name="FutsalSchedule")
public class FutsalSchedule extends AbstractEntity implements IAbstractEntity,Serializable{
    private int starthour;
    private int endhour;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date scheduledate; 
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "futsalid")
    private Futsal futsal;
    
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

    @Override
    public String getTableName() {
        return "FutsalSchedule";
    }
    
    public Futsal getFutsal() {
        return futsal;
    }

    public void setFutsal(Futsal futsal) {
        this.futsal = futsal;
    }
}