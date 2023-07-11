package com.saugat.beans;

import Entities.SlotSchedule;
import com.saugat.bean.enums.SlotType;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class SlotScheduleBean implements Serializable {

    private SlotSchedule slotSchedule;

    public SlotSchedule getSlotSchedule() {
        return slotSchedule;
    }

    public void setSlotSchedule(SlotSchedule slotSchedule) {
        this.slotSchedule = slotSchedule;
    }

    public boolean getIsAutomatic() {
        return slotSchedule != null && slotSchedule.getSlotType().equals(SlotType.automatic);
    }
    
    public boolean getIsShift() {
        return slotSchedule != null && slotSchedule.getSlotType().equals(SlotType.shift);
    }
    public boolean getIsCustom() {
        return slotSchedule != null && slotSchedule.getSlotType().equals(SlotType.custom);
    }
    
}
