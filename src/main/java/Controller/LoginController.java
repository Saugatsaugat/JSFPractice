/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.User;
import Login.Login;
import Model.UserCRUD;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
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
        try {

            User userRecord = userCRUD.findByUsernameAndPassword(login.getUsername(), login.getPassword());

            if (userRecord != null) {
                Map<String, Object> flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
                flash.put("user", userRecord);
                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext externalContext = context.getExternalContext();
                externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/Home/home.xhtml");
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Credentials", "Invalid Credentials");
                context.addMessage(null, message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
