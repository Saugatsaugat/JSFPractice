/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author saugat
 */
@Entity
@Table(name="FutsalUserRelation")
public class FutsalUserRelation extends AbstractEntity implements IAbstractEntity, Serializable {

    @Temporal(javax.persistence.TemporalType.DATE)
    Date entrydate;
    Long userid;
    Long futsalid;

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getFutsalid() {
        return futsalid;
    }

    public void setFutsalid(Long futsalid) {
        this.futsalid = futsalid;
    }

    @Override
    public String getTableName() {
        return "FutsalUserRelation";

    }

}
