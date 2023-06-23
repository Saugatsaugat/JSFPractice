package Controller;

import Entities.User;
import Model.UserCRUD;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
public class RegisterController implements Serializable {

    @Inject
    private UserCRUD userCRUD;

    private User user;
    private User newUser;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PostConstruct
    public void init() {
        user = new User();
        newUser = new User();

    }

    public void saveUser() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        newUser.setUsertype(user.getUsertype());
        newUser.setFirstname(user.getFirstname());
        newUser.setMidname(user.getMidname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setMobile(user.getMobile());
        newUser.setUserpassword(new PasswordHashController().getPasswordHash(user.getUserpassword()));
        
        try {
            boolean status = userCRUD.saveUser(newUser);
            if (status) {
                try {
                    context.getExternalContext().redirect("faces/view/Home/home.xhtml");
                    System.out.println("Success");
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
