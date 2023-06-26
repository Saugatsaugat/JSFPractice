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

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class UserController implements Serializable {

    @Inject
    private UserCrud userCrud;

    @Inject
    private FutsalCrud futsalCrud;

    @Inject
    private FutsalUserRelationCrud futsalUserRelationCrud;

    List<User> userList;
    private User user;
    private FutsalUserRelation futsalUserRelation;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

    }

    @PostConstruct
    public void init() {
        user = new User();
        futsalUserRelation = new FutsalUserRelation();
        userList = userCrud.getAllData();
    }

    public void deleteUser() {
        if (user.getId() != null) {
            futsalUserRelation = futsalUserRelationCrud.getFutsalUserRelationByUserId(user.getId());
            if (futsalUserRelation != null) {
                if (futsalUserRelationCrud.deleteById(futsalUserRelation.getId())) {
                    Futsal futsal = futsalCrud.checkIfFutsalRegistered(user.getId());
                    if (futsal != null) {
                        if (futsalCrud.deleteById(futsal.getId())) {
                            boolean status = userCrud.deleteById(user.getId());
                            if (status) {
                                try {
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    ExternalContext externalContext = context.getExternalContext();
                                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserView/userTable.xhtml");
                                } catch (Exception e) {

                                }

                            } else {
                                FacesContext context = FacesContext.getCurrentInstance();
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                                context.addMessage(null, message);

                            }
                        }
                    } else {
                        boolean status = userCrud.deleteById(user.getId());
                        if (status) {
                            try {
                                FacesContext context = FacesContext.getCurrentInstance();
                                ExternalContext externalContext = context.getExternalContext();
                                externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserView/userTable.xhtml");
                            } catch (Exception e) {

                            }

                        } else {
                            FacesContext context = FacesContext.getCurrentInstance();
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                            context.addMessage(null, message);

                        }
                    }
                }
            }

        }
    }

    public void saveUser() {
        if (user.getId() != null) {
            boolean status = userCrud.update(user, user.getId());
            if (status) {
                try {
                    FacesContext context = FacesContext.getCurrentInstance();
                    ExternalContext externalContext = context.getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserView/userTable.xhtml");
                } catch (Exception e) {

                }
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edit Failed", "Edit Failed");
                context.addMessage(null, message);

            }
        } else {
            try {

                boolean status = userCrud.saveUser(user);
                if (status) {
                    try {
                        FacesContext context = FacesContext.getCurrentInstance();
                        ExternalContext externalContext = context.getExternalContext();
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserView/userTable.xhtml");
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
