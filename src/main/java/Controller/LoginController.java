/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Login.Login;
import Model.UserCRUD;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
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

public class LoginController implements Serializable {

    private Login login;
    @Inject
    private UserCRUD userCRUD;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @PostConstruct
    public void init() {
        login = new Login();
    }

    public void checkUser() throws SQLException, ClassNotFoundException, IOException {
//        try {
            FacesContext context = FacesContext.getCurrentInstance();
             context.getExternalContext().redirect("faces/view/Home/home.xhtml");
//            User userRecord = userCRUD.findByUsernameAndPassword(login.getUsername(), login.getPassword());
//
//            if (userRecord != null) {
//                Map<String, Object> flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
//                flash.put("user", userRecord);
//               // context.getExternalContext().redirect("faces/view/Home/home.xhtml?faces-redirect=true");
////               context.getExternalContext().redirect("faces/view/Home/eastHome.xhtml");
//            } else {
//                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Credentials", "Invalid Credentials");
//                context.addMessage(null, message);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

    }
}
