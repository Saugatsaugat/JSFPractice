package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "Futsal")
public class Futsal extends AbstractEntity<Futsal> implements IAbstractEntity, Serializable {
    private String name;
    private Long pan;
    private String address;
    private Long mobile;
    private BigDecimal rate;
    private Long ownerid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPan() {
        return pan;
    }

    public void setPan(Long pan) {
        this.pan = pan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Long ownerid) {
        this.ownerid = ownerid;
    }

    @Override
    public String getTableName() {
        return "Futsal";
    }

}
