package Entities;

import com.saugat.bean.enums.SlotType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author saugat
 */
public class SlotSchedule implements Serializable {

    @Enumerated(EnumType.STRING)
    private SlotType slotType;
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

    public SlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }
    

}
