package Controller;

import Entities.Futsal;
import Entities.FutsalUserRelation;
import Entities.User;
import Model.FutsalCrud;
import Model.FutsalUserRelationCrud;
import Model.UserCrud;
import com.saugat.bean.enums.UserType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
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
public class FutsalController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;

    @Inject
    private FutsalCrud futsalCrud;

    @Inject
    private UserCrud userCrud;

    @Inject
    private FutsalUserRelationCrud futsalUserRelationCrud;

    List<Futsal> futsalList;
    private Futsal futsal;
    private FutsalUserRelation futsalUserRelation;

    public List<Futsal> getFutsalList() {
        return futsalList;
    }

    public void setFutsalList(List<Futsal> futsalList) {
        this.futsalList = futsalList;
    }

    public Futsal getFutsal() {
        return futsal;
    }

    public void setFutsal(Futsal futsal) {
        this.futsal = futsal;
    }

    @PostConstruct
    public void init() {
        futsal = new Futsal();
        futsalUserRelation = new FutsalUserRelation();
        futsalList = futsalCrud.getAllData(Futsal.class);
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

    public void updateFutsal() {
        if (futsal.getId() != null) {
            if (futsalCrud.update(futsal, futsal.getId())) {
                try {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Futsal Updated Successfully", "Futsal Updated Successfully");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Flash flash = externalContext.getFlash();
                    flash.setKeepMessages(true);
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalTable.xhtml");
                } catch (Exception e) {
                }
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "Update Failed");
        context.addMessage(null, message);

    }

    public void saveFutsal() {
        if (futsal.getId() == null) {
            if (futsal.getOwnerid() != null) {
                User userData = userCrud.getDataById(futsal.getOwnerid());
                futsal.setMobile(userData.getMobile());
                futsal.setOwnerid(userData.getId());
            } else {
                if (session.getAttribute("userId") != null) {
                    Long userId = (Long) session.getAttribute("userId");
                    User userData = userCrud.getDataById(userId);
                    futsal.setMobile(userData.getMobile());
                    futsal.setOwnerid(userId);
                }
            }

            if (futsalCrud.save(futsal)) {
                try {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Futsal Created Successfully", "Futsal Created Successfully");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Flash flash = externalContext.getFlash();
                    flash.setKeepMessages(true);
                    if ((userCrud.getDataById(futsal.getOwnerid()).getUsertype() == UserType.admin)) {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalTable.xhtml");
                    } else if ((userCrud.getDataById(futsal.getOwnerid()).getUsertype() == UserType.futsalowner)) {
                        session.setAttribute("futsalId", futsal.getId());
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalOwnerUI/Home/home.xhtml");
                    }

                } catch (Exception e) {
                }
            }

        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save Failed", "Save Failed");
        context.addMessage(null, message);

    }

    public void deleteFutsal() {
        Long futsalId = futsal.getId();
        if (futsalId != null) {
            futsal = futsalCrud.getDataById(futsalId);
            if ((futsal != null)) {
                boolean status = futsalCrud.deleteById(futsal.getId());
                if (status) {
                    try {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Futsal Deleted Successfully", "Futsal Deleted Successfully");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        externalContext = FacesContext.getCurrentInstance().getExternalContext();
                        Flash flash = externalContext.getFlash();
                        flash.setKeepMessages(true);
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalTable.xhtml");
                    } catch (Exception e) {
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                    context.addMessage(null, message);
                }

            }

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
            context.addMessage(null, message);

        }
    }

}
