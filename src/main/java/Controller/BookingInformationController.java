/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.BookingInformation;
import Entities.FutsalSchedule;
import Model.BookingInformationCrud;
import java.io.Serializable;
import java.util.Date;
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
public class BookingInformationController implements Serializable {

    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;

    private List<BookingInformation> bookingInformationList;
    private BookingInformation bookingInformation;
    private FutsalSchedule futsalSchdule;

    public List<BookingInformation> getBookingInformationList() {
        return bookingInformationList;
    }

    public void setBookingInformationList(List<BookingInformation> bookingInformationList) {
        this.bookingInformationList = bookingInformationList;
    }

    public BookingInformation getBookingInformation() {
        return bookingInformation;
    }

    public void setBookingInformation(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public FutsalSchedule getFutsalSchdule() {
        return futsalSchdule;
    }

    public void setFutsalSchdule(FutsalSchedule futsalSchdule) {
        this.futsalSchdule = futsalSchdule;
    }

    @Inject
    private BookingInformationCrud bookingInformationCrud;

    @PostConstruct
    public void init() {
        bookingInformationList = bookingInformationCrud.getAllData(BookingInformation.class);
        bookingInformation = new BookingInformation();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
    }

    public void bookingInformationSchedule(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public void setBookingInformationId(Long id) {
        this.bookingInformation.setId(id);
    }

    public void save() {
        if (bookingInformation.getId() != null) {
            if (session.getAttribute("userId") != null) {
                Long userId = (Long) session.getAttribute("userId");

                if (bookingInformationCrud.update(bookingInformation, bookingInformation.getId())) {
                    try {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/BookingInformation/bookingInformationTable.xhtml");
                    } catch (Exception e) {

                    }
                } else {

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Failed", "Add Failed");
                    context.addMessage(null, message);

                }
            }

        } else {
            if (session.getAttribute("userId") != null) {
                Long userId = (Long) session.getAttribute("userId");
                bookingInformation.setUserid(userId);
                bookingInformation.setEntrydate(new Date());
                if (bookingInformationCrud.save(bookingInformation)) {
                    try {
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/BookingInformation/bookingInformationTable.xhtml");
                    } catch (Exception e) {

                    }
                } else {

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "Update Failed");
                    context.addMessage(null, message);

                }

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Must Login to Continue", "Login to continue");
                context.addMessage(null, message);
            }
        }
    }

    public void delete() {
        if (bookingInformation.getId() != null) {
            if (bookingInformationCrud.deleteById(bookingInformation.getId())) {
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/faces/view/BookingInformation/bookingInformationTable.xhtml");
                } catch (Exception e) {

                }
            } else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deletion Failed", "Deletion Failed");
                context.addMessage(null, message);

            }
        }
    }

}
