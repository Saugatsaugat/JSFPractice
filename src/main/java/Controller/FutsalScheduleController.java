package Controller;

import Entities.Futsal;
import Entities.FutsalSchedule;
import Model.FutsalCrud;
import Model.FutsalScheduleCruds;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
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

    private List<FutsalSchedule> futsalScheduleList;
    private FutsalSchedule futsalSchedule;

    public List<FutsalSchedule> getFutsalScheduleList() {
        return futsalScheduleList;
    }

    public void setFutsalScheduleList(List<FutsalSchedule> futsalScheduleList) {
        this.futsalScheduleList = futsalScheduleList;
    }

    public FutsalSchedule getFutsalSchedule() {
        return futsalSchedule;
    }

    public void setFutsalSchedule(FutsalSchedule futsalSchedule) {
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
        futsalSchedule = new FutsalSchedule();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
        newDate = new Date();
        futsal = new Futsal();
        futsalScheduleList = fsc.getCurrentDateFutsalSchedule();

    }

    public void onDateSelect(SelectEvent event) {
        futsalScheduleList = null;
        newDate = (Date) event.getObject();

        futsalScheduleList = fsc.getFutsalScheduleByDateAndUserId(newDate, futsal.getId());

    }

    public void showFutsalSchedule(Futsal futsal) {
        this.futsal = futsal;
        this.futsalScheduleList = new ArrayList<>();
    }

    public void showFutsalSchedule(FutsalSchedule futsalSchedule) {
        this.futsalSchedule = futsalSchedule;
    }

    public void setFutsalScheduleId(Long id) {
        futsalSchedule.setId(id);
    }

    public void save() {
        if (futsalSchedule.getId() != null) {
            if (session.getAttribute("userId") != null) {
                Long userId = (Long) session.getAttribute("userId");
                futsal = futsalCrud.checkIfFutsalRegistered(userId);
                Long futsalId = futsal.getId();
                futsalSchedule.setFutsalid(futsalId);
                if (futsalSchedule.getStatus() == null) {
                    futsalSchedule.setStatus("available");
                }
                if (fsc.update(futsalSchedule, futsalSchedule.getId())) {
                    try {

                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                    } catch (Exception e) {

                    }
                } else {

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "Update Failed");
                    context.addMessage(null, message);

                }
            }
        }

    
        else {
            if (session.getAttribute("userId") != null) {
            Long userId = (Long) session.getAttribute("userId");
            futsal = futsalCrud.checkIfFutsalRegistered(userId);
            Long futsalId = futsal.getId();
            futsalSchedule.setFutsalid(futsalId);
            if (futsalSchedule.getStatus() == null) {
                futsalSchedule.setStatus("available");
            }

            if (fsc.save(futsalSchedule)) {
                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Failed", "Add Failed");
                context.addMessage(null, message);

            }

        }
    }
}

public void delete() {
        if (futsalSchedule.getId() != null) {
            if (fsc.deleteById(futsalSchedule.getId())) {
                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalSchedule/futsalScheduleTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }
        }
    }
}