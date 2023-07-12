
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author saugat
 */
public class NormalSchedule implements Serializable{
    private Date dateFrom;
    private Date dateTo;
    private Map<String,BigDecimal> hoursRate;

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

    public Map<String,BigDecimal> getHoursRate() {
        return hoursRate;
    }

    public void setHoursRate(Map<String,BigDecimal> hoursRate) {
        this.hoursRate = hoursRate;
    }

    public NormalSchedule() {
    }
    
    
    
    
}
