package Controller;

import Entities.User;
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
    private UserCrud userCRUD;

    List<User> userList;
    private User user;
    private User newUser;

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
        System.out.println("Hello");
        this.user = user;

    }

    @PostConstruct
    public void init() {
        user = new User();
        newUser = new User();
        userList = userCRUD.getAllData();
    }

    public void deleteUser() {
        if (user.getId() != null) {
            boolean status = userCRUD.deleteById(user.getId());
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

    public void saveUser() {
        if (user.getId() != null) {
            boolean status = userCRUD.update(user, user.getId());
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

                boolean status = userCRUD.saveUser(user);
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