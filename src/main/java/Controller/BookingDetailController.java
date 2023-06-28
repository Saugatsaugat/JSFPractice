/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.BookingDetailCrud;
import java.io.Serializable;
import javax.annotation.PostConstruct;
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
public class BookingDetailController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;

    @Inject
    private BookingDetailCrud bookingDetailCrud;
    
    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

}
