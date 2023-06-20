/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@RequestScoped

public class LoginController {

    public void checkUser() throws SQLException, ClassNotFoundException, IOException {
        try {
            Connection con = new JDBCController().getCon();
            FacesContext context = FacesContext.getCurrentInstance();

            Map<String, String> requestParams = context.getExternalContext().getRequestParameterMap();

            String username = requestParams.get("form:username");
            String password = requestParams.get("form:password");

            String getData = "Select * from test where username=? and password=?;";
            PreparedStatement ps = con.prepareStatement(getData);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                context.getExternalContext().redirect("faces/home.xhtml");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Credentials", "Invalid Credentials");
                context.addMessage(null, message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
