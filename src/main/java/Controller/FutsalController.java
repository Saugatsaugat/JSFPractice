/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.Futsal;
import Entities.FutsalUserRelation;
import Entities.User;
import Model.FutsalCrud;
import Model.FutsalUserRelationCrud;
import Model.UserCrud;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
        futsalList = futsalCrud.getAllData();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

    public void deleteFutsal() {
        if (futsal.getId() != null) {
            boolean status = futsalCrud.deleteById(futsal.getId());
            if (status) {
                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalView/futsalTable.xhtml");
                } catch (Exception e) {

                }

            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }
        }
    }

    public void saveFutsal() {
        if (futsal.getId() != null) {
            boolean status = futsalCrud.update(futsal, futsal.getId());
            if (status) {
                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalView/futsalTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edit Failed", "Edit Failed");
                context.addMessage(null, message);

            }
        } else {
            try {
                Long userId = (Long) session.getAttribute("userId");
                User user = userCrud.getUserById(userId);
                futsal.setOwnerid(userId);
                futsal.setMobile(user.getMobile());
                boolean futsalstatus = futsalCrud.saveFutsal(futsal);
                if (futsalstatus) {
                    Long futsalId = futsal.getId();
                    futsalUserRelation.setUserid(userId);
                    futsalUserRelation.setFutsalid(futsalId);
                    boolean futsaluserstatus = futsalUserRelationCrud.create(futsalUserRelation);
                    if (futsaluserstatus) {

                        try {
                            externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalView/futsalTable.xhtml");
                        } catch (Exception e) {

                        }
                    } else {

                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Failed", "Registration Failed");
                        context.addMessage(null, message);
                    }
                } else {

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Failed", "Registration Failed");
                    context.addMessage(null, message);
                }

            } catch (Exception e) {

            }

        }
    }
}
