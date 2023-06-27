package Controller;

import Entities.Futsal;
import Entities.FutsalSchedule;
import Model.FutsalCrud;
import Model.FutsalScheduleCruds;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class FutsalScheduleController implements Serializable {

    @Inject
    private FutsalCrud futsalCrud;
    @Inject
    private FutsalScheduleCruds fsc;

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;
    private Futsal futsal;
    private Date newDate;

    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }

    List<FutsalSchedule> futsalSchedule;

    public List<FutsalSchedule> getFutsalSchedule() {
        return futsalSchedule;
    }

    public void setFutsalSchedule(List<FutsalSchedule> futsalSchedule) {
        this.futsalSchedule = futsalSchedule;
    }

    public Futsal getFutsal() {
        return futsal;
    }

    public void setFutsal(Futsal futsal) {
        this.futsal = futsal;
    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
        newDate = new Date();
        futsal = new Futsal();
        futsalSchedule=null;
//        futsalSchedule = fsc.getCurrentDateFutsalSchedule();

    }

    public void onDateSelect(SelectEvent event) {
        futsalSchedule = null;
        newDate = (Date) event.getObject();
        
        futsalSchedule = fsc.getFutsalScheduleByDateAndUserId(newDate,futsal.getId());
       
    }

    public void displayDialog() {
        RequestContext contextReq = RequestContext.getCurrentInstance();
        contextReq.execute("PF('bookNowDialog').show();");
    }

}
