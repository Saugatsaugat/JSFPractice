package Controller;

import Entities.Futsal;
import Entities.FutsalUserRelation;
import Entities.User;
import Model.FutsalCrud;
import Model.FutsalUserRelationCrud;
import Model.UserCrud;
import com.saugat.bean.enums.ActionType;
import com.saugat.bean.enums.ResourceType;
import com.saugat.bean.enums.UserType;
import com.saugat.beans.UserBean;
import com.saugat.interceptors.Acl;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
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
    @Inject
    private UserBean userBean;

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
        userList = userCrud.getAllData();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

    public void resetUser() {
        this.user = new User();
    }

    @Acl(actionName = ActionType.UPDATE, resourceName = ResourceType.USER)
    public void updateUser() {
        if (user.getId() != null) {
            user.setUserpassword(new PasswordHashController().getPasswordHash(user.getUserpassword()));
            if (userCrud.update(user, user.getId())) {
                try {
                    ValidationMessageGenerationUtil.validationMessageGeneration("Updated", "informational");
                    externalContext.redirect(externalContext.getRequestContextPath());

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
                    ValidationMessageGenerationUtil.validationMessageGeneration("Added", "informational");
                    if (userBean.getUser() == null) {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserUI/Home/home.xhtml");
                    }
                    User sessionUser = userBean.getUser();
                    if (sessionUser.getUsertype() == UserType.admin) {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");
                    } else {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/UserUI/Home/home.xhtml");
                    }

                } catch (Exception e) {
                }
            }
        }
        ValidationMessageGenerationUtil.validationMessageGeneration("Creation Failed", "error");

    }

    public void deleteUser() {
        if (user.getId() != null) {
            if (userCrud.deleteById(user.getId())) {
                try {
                    ValidationMessageGenerationUtil.validationMessageGeneration("Deleted", "informational");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/AdminUI/Home/userTable.xhtml");

                } catch (Exception e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            e.getMessage(), e.getMessage());
                    context.addMessage(null, message);
                }
                ValidationMessageGenerationUtil.validationMessageGeneration("Delete Failed", "error");

            }

        }
    }

    public void updateUserForUserEdit() {
        User user = userBean.getUser();
        this.setUser(user);
    }

}
