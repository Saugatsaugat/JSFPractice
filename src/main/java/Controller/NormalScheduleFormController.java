package Controller;

import Entities.Futsal;
import Entities.NormalSchedule;
import Model.FutsalCrud;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class NormalScheduleFormController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;
    
    @Inject
    private FutsalCrud futsalCrud;
    
    private Map<String, BigDecimal> initialHoursRatesList;

    public Map<String, BigDecimal> getInitialHoursRatesList() {
        return initialHoursRatesList;
    }

    public void setInitialHoursRatesList(Map<String, BigDecimal> initialHoursRatesList) {
        this.initialHoursRatesList = initialHoursRatesList;
    }
    
    private NormalSchedule normalSchedule;

    public NormalSchedule getNormalSchedule() {
        return normalSchedule;
    }

    public void setNormalSchedule(NormalSchedule normalSchedule) {
        this.normalSchedule = normalSchedule;
    }
    
    
    @PostConstruct
    public void init() {
        initialHoursRatesList = new LinkedHashMap<>();
        normalSchedule = new NormalSchedule();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
        BigDecimal rate = new BigDecimal("1000");
        initialHoursRatesList.put("12-1", rate);
        for(int i=1;i<25;i++){
            initialHoursRatesList.put(i+"-"+(i+1), rate);
        }
    }
    
    public void initHoursAndRatesList(){
        Long userId = (Long) session.getAttribute("userId");
        Futsal futsal = futsalCrud.checkIfFutsalRegistered(userId);
        BigDecimal rate = futsal.getRate();
        initialHoursRatesList.put("12-1", rate);
        for(int i=1;i<25;i++){
            initialHoursRatesList.put(i+"-"+(i+1), rate);
        }
    }


}
