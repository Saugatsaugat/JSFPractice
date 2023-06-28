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
    
    

    @Override
    public String getTableName() {
        return "BookingDetail";
    }
    
}
