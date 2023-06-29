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
    private Futsal futsal;
    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;

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
        futsal = new Futsal();
        futsalUserRelation = new FutsalUserRelation();
        userList = userCrud.getAllData(User.class);
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

//delete user, futsal detail if present, and Futsaluserrelation table data if present
    public void deleteUser() {
        if (user.getId() != null) {
            Long checkId = user.getId();

            if (((futsalUserRelationCrud.getFutsalUserRelationByUserId(checkId)) == null) && (futsalCrud.checkIfFutsalRegistered(checkId))==null) {
                boolean status = userCrud.deleteById(checkId);
                if (status) {
                    try {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");
                    } catch (Exception e) {
                    }
                }
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            } else if ((futsalUserRelationCrud.getFutsalUserRelationByUserId(user.getId()) != null) && futsalCrud.checkIfFutsalRegistered(user.getId())!=null)  {
                if (futsalUserRelationCrud.deleteById(futsalUserRelation.getId())) {
                    if (futsalCrud.deleteById(futsal.getId())) {
                        boolean status = userCrud.deleteById(user.getId());
                        if (status) {
                            try {
                                externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");
                            } catch (Exception e) {
                            }
                        }
                    }

                }
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }
        }
    }

    /**
     *
     * @author saugat Function: saveUser()
     */
    public void saveUser() {
        if (user.getId() != null) {
            boolean status = userCrud.update(user, user.getId());
            if (status) {
                try {

                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edit Failed", "Edit Failed");
                context.addMessage(null, message);

            }
        } else {
            try {

                boolean status = userCrud.save(user);
                if (status) {
                    try {

                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");
                    } catch (Exception e) {

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
