/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.Futsal;
import Model.FutsalCrud;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class FutsalController implements Serializable {

    @Inject
    private FutsalCrud futsalCrud;

    List<Futsal> futsalList;
    private Futsal futsal;

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
        futsalList = futsalCrud.getAllData();
    }

    public void deleteFutsal() {
        if (futsal.getId() != null) {
            boolean status = futsalCrud.deleteById(futsal.getId());
            if (status) {
                try {
                    FacesContext context = FacesContext.getCurrentInstance();
                    ExternalContext externalContext = context.getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalView/futsalTable.xhtml");
                } catch (Exception e) {

                }

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
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
                    FacesContext context = FacesContext.getCurrentInstance();
                    ExternalContext externalContext = context.getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalView/futsalTable.xhtml");
                } catch (Exception e) {

                }
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edit Failed", "Edit Failed");
                context.addMessage(null, message);

            }
        } else {
            try {

                boolean status = futsalCrud.saveFutsal(futsal);
                if (status) {
                    try {
                        FacesContext context = FacesContext.getCurrentInstance();
                        ExternalContext externalContext = context.getExternalContext();
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/FutsalView/futsalTable.xhtml");
                    } catch (Exception e) {

                    }
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Failed", "Registration Failed");
                    context.addMessage(null, message);
                }

            } catch (Exception e) {

            }

        }
    }
}
