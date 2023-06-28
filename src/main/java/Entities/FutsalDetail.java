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
@Table(name = "FutsalDetail")
public class FutsalDetail extends AbstractEntity implements IAbstractEntity, Serializable {
    private float length;
    private float breadth;
    private boolean dressingroom;
    private boolean washroom;
    private Long futsalid;

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getBreadth() {
        return breadth;
    }

    public void setBreadth(float breadth) {
        this.breadth = breadth;
    }

    public boolean isDressingroom() {
        return dressingroom;
    }

    public void setDressingroom(boolean dressingroom) {
        this.dressingroom = dressingroom;
    }

    public boolean isWashroom() {
        return washroom;
    }

    public void setWashroom(boolean washroom) {
        this.washroom = washroom;
    }

    public Long getFutsalid() {
        return futsalid;
    }

    public void setFutsalid(Long futsalid) {
        this.futsalid = futsalid;
    }
    

    @Override
    public String getTableName() {
        return "FutsalDetail";
    }

}
