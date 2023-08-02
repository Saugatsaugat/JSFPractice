/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.FutsalUserRelation;
import Model.FutsalUserRelationCrud;

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
public class FutsalUserRelationController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;
    @Inject
    private FutsalUserRelationCrud futsalUserRelationCrud;

    private List<FutsalUserRelation> futsalUserRelationList;
    private FutsalUserRelation futsalUserRelation;
    private List<Object> totalFutsalUserTable;

    public List<Object> getTotalFutsalUserTable() {
        return totalFutsalUserTable;
    }

    public void setTotalFutsalUserTable(List<Object> totalFutsalUserTable) {
        this.totalFutsalUserTable = totalFutsalUserTable;
    }

    public List<FutsalUserRelation> getFutsalUserRelationList() {
        return futsalUserRelationList;
    }

    public void setFutsalUserRelationList(List<FutsalUserRelation> futsalUserRelationList) {
        this.futsalUserRelationList = futsalUserRelationList;
    }

    public FutsalUserRelation getFutsalUserRelation() {
        return futsalUserRelation;
    }

    public void setFutsalUserRelation(FutsalUserRelation futsalUserRelation) {
        this.futsalUserRelation = futsalUserRelation;
    }

    @PostConstruct
    public void init() {
        totalFutsalUserTable = futsalUserRelationCrud.getAllDataFutsalUser();
        futsalUserRelation = new FutsalUserRelation();
        futsalUserRelationList = futsalUserRelationCrud.getAllData();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

    public void showFutsalUserRelation(FutsalUserRelation futsalUserRelation) {
        this.futsalUserRelation = futsalUserRelation;
    }

    public void setFutsalUserRelationId(Long id) {
        this.futsalUserRelation.setId(id);
    }

    public void update() {
        if (futsalUserRelation.getId() != null) {

            if (futsalUserRelationCrud.update(futsalUserRelation, futsalUserRelation.getId())) {
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalUserRelationTable.xhtml");
                } catch (Exception e) {

                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "Update Failed");
                context.addMessage(null, message);
            }
        }
    }

    public void save() {
        if (futsalUserRelationCrud.save(futsalUserRelation)) {
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalUserRelationTable.xhtml");
            } catch (Exception e) {

            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Failed", "Add Failed");
            context.addMessage(null, message);
        }
    }

    public void delete() {
        if (futsalUserRelation.getId() != null) {
            if (futsalUserRelationCrud.deleteById(futsalUserRelation.getId())) {
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/futsalUserRelationTable.xhtml");
                } catch (Exception e) {

                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);
            }
        }
    }

}
