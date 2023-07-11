package Entities;

import com.saugat.bean.enums.SlotType;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private Date dateFrom;
    private Date dateTo;
    @Temporal(TemporalType.TIME)
    private Date breakTimeFrom;
    @Temporal(TemporalType.TIME)
    private Date breakTimeTo;
    @Temporal(TemporalType.TIME)
    private Date TimeFrom;
    @Temporal(TemporalType.TIME)
    private Date TimeTo;
    private BigDecimal morningShiftRate;
    private BigDecimal dayShiftRate;
    private BigDecimal eveningShiftRate;
    private BigDecimal nightShiftRate;
    private BigDecimal normalRate;

    public Date getTimeFrom() {
        return TimeFrom;
    }

    public void setTimeFrom(Date TimeFrom) {
        this.TimeFrom = TimeFrom;
    }

    public Date getTimeTo() {
        return TimeTo;
    }

    public void setTimeTo(Date TimeTo) {
        this.TimeTo = TimeTo;
    }

    
    public BigDecimal getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(BigDecimal normalRate) {
        this.normalRate = normalRate;
    }
    
    public BigDecimal getMorningShiftRate() {
        return morningShiftRate;
    }

    public void setMorningShiftRate(BigDecimal morningShiftRate) {
        this.morningShiftRate = morningShiftRate;
    }

    public BigDecimal getDayShiftRate() {
        return dayShiftRate;
    }

    public void setDayShiftRate(BigDecimal dayShiftRate) {
        this.dayShiftRate = dayShiftRate;
    }

    public BigDecimal getEveningShiftRate() {
        return eveningShiftRate;
    }

    public void setEveningShiftRate(BigDecimal eveningShiftRate) {
        this.eveningShiftRate = eveningShiftRate;
    }

    public BigDecimal getNightShiftRate() {
        return nightShiftRate;
    }

    public void setNightShiftRate(BigDecimal nightShiftRate) {
        this.nightShiftRate = nightShiftRate;
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
