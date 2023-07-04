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
        if (session.getAttribute("futsalId") != null) {
            Long futsalId = (Long) session.getAttribute("futsalId");
            futsalScheduleList = fsc.getCurrentDateDataByFutsalId(futsalId);
        } else {
            futsalScheduleList = fsc.getCurrentDateFutsalSchedule();
        }

    }

    public void updateFutsalSchedule(Long futsalId) {
        if (futsalId != null) {
            futsalScheduleList = fsc.getCurrentDateDataByFutsalId(futsalId);
        }
    }

    public void onDateSelect(SelectEvent event) {
        futsalScheduleList = null;
        newDate = (Date) event.getObject();
        futsalScheduleList = fsc.getFutsalScheduleByDateAndUserId(newDate, futsal.getId());
    }

    public void futsalScheduleByFutsalId(Long futsalId) {
        if (futsalId != null) {
            try {
                futsalScheduleList = fsc.getCurrentDateDataByFutsalId(futsalId);
            } catch (Exception e) {

            }
        }
    }

    public void showFutsalSchedule(FutsalSchedule futsalSchedule) {
        this.futsalSchedule = futsalSchedule;
    }

    public void update() {
        if (futsalSchedule.getId() != null) {
            if (session.getAttribute("userId") != null) {
                Long userId = (Long) session.getAttribute("userId");
                futsal = futsalCrud.checkIfFutsalRegistered(userId);
                Long futsalId = futsal.getId();
                futsalSchedule.setFutsal(futsalCrud.getDataById(futsalId));
                if (futsalSchedule.getStatus() == null) {
                    futsalSchedule.setStatus("available");
                }
                if (fsc.update(futsalSchedule, futsalSchedule.getId())) {
                    try {

                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalOwnerUI/Home/futsalScheduleTable.xhtml");
                    } catch (Exception e) {

                    }
                }
            }
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "Update Failed");
        context.addMessage(null, message);

    }

    public void save() {
        if (session.getAttribute("userId") != null) {
            Long userId = (Long) session.getAttribute("userId");
            futsal = futsalCrud.checkIfFutsalRegistered(userId);
            Long futsalId = futsal.getId();
            futsalSchedule.setFutsal(futsalCrud.getDataById(futsalId));
            if (futsalSchedule.getStatus() == null) {
                futsalSchedule.setStatus("available");
            }

            if (fsc.save(futsalSchedule)) {
                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalOwnerUI/Home/futsalScheduleTable.xhtml");
                } catch (Exception e) {

                }
            }

        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Failed", "Add Failed");
        context.addMessage(null, message);

    }

    public boolean saveFutsal(Futsal futsal) {
        if (session.getAttribute("userId") == null) {

            context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Required", "Must Login to Continue!");
            context.addMessage(null, message);
            return false;
        } else {
            this.futsal = futsal;
            this.futsalScheduleList = new ArrayList<>();
            return true;
        }

    }

    public void setFutsalScheduleId(Long id) {
        futsalSchedule.setId(id);
    }

    public void delete() {
        if (futsalSchedule.getId() != null) {
            if (fsc.deleteById(futsalSchedule.getId())) {
                futsalSchedule = fsc.getDataById(futsalSchedule.getId());

                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalScheduleTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }
        }
    }

    public void bookFutsalSchedule() {
        if (session.getAttribute("userId") == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Required", "Login Required");
            context.addMessage(null, message);
        } else {

        }

    }

}
