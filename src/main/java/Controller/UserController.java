package Controller;

import Entities.Futsal;
import Entities.FutsalUserRelation;
import Entities.User;
import Model.FutsalCrud;
import Model.FutsalUserRelationCrud;
import Model.UserCrud;
import com.saugat.bean.enums.UserType;
import java.io.Serializable;
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
import org.primefaces.context.RequestContext;

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

    public void resetUser() {
        this.user = new User();
    }

    public void updateUser() {
        if (user.getId() != null) {
            user.setUserpassword(new PasswordHashController().getPasswordHash(user.getUserpassword()));
            if (userCrud.update(user, user.getId())) {
                try {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data Updated Successfully", "Data Updated Successfully");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Flash flash = externalContext.getFlash();
                    flash.setKeepMessages(true);
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");

                } catch (Exception e) {
                }
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Updation Failed", "Update Failed");
        context.addMessage(null, message);

    }

    public void saveUser() {
        if (user.getId() == null) {
            user.setUserpassword(new PasswordHashController().getPasswordHash(user.getUserpassword()));
            if (user.getUsertype() == null) {
                user.setUsertype(UserType.user);
            }
            if (userCrud.save(user)) {
                try {
                    if (session.getAttribute("userId") != null) {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Added Successfully", "Added Successfully");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        externalContext = FacesContext.getCurrentInstance().getExternalContext();
                        Flash flash = externalContext.getFlash();
                        flash.setKeepMessages(true);
                        User sessionUser = userCrud.getDataById((Long) session.getAttribute("userId"));
                        if (sessionUser.getUsertype() == UserType.admin) {
                            externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");
                        } else {
                            externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserUI/Home/home.xhtml");
                        }
                    } else {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Created Successfully", "User Created Successfully");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        externalContext = FacesContext.getCurrentInstance().getExternalContext();
                        Flash flash = externalContext.getFlash();
                        flash.setKeepMessages(true);

                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserUI/Home/home.xhtml");
                    }
                } catch (Exception e) {
                }
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Creation Failed", "Creation Failed");
        context.addMessage(null, message);

    }

    public void deleteUser() {
        if (user.getId() != null) {
            if (userCrud.deleteById(user.getId())) {
                try {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data Deleted Successfully", "Data Deleted Successfully");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Flash flash = externalContext.getFlash();
                    flash.setKeepMessages(true);
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");

                } catch (Exception e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            e.getMessage(), e.getMessage());
                    context.addMessage(null, message);
                }
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }

            }
        }



}
