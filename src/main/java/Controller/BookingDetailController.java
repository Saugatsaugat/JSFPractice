/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.BookingDetail;
import Entities.Futsal;
import Entities.User;
import Model.BookingDetailCrud;
import Model.FutsalCrud;
import Model.UserCrud;
import com.saugat.bean.enums.UserType;
import java.io.Serializable;
import java.util.List;
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

    @Inject
    private FutsalCrud futsalCrud;
    FacesContext context;
    ExternalContext externalContext;
    HttpSession session;
    private User user;
    private BookingDetail bookingDetail;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private List<BookingDetail> bookingDetailList;

    public List<BookingDetail> getBookingDetailList() {
        return bookingDetailList;
    }

    public void setBookingDetailList(List<BookingDetail> bookingDetailList) {
        this.bookingDetailList = bookingDetailList;
    }

    public BookingDetail getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    @Inject
    private BookingDetailCrud bookingDetailCrud;
    @Inject
    private UserCrud userCrud;

    @PostConstruct
    public void init() {
        bookingDetail = new BookingDetail();
        user = new User();
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        session = (HttpSession) externalContext.getSession(true);
        Long sessionUserId = (Long) session.getAttribute("userId");
        if (sessionUserId != null) {
            user = userCrud.getDataById(sessionUserId);
            if (user != null) {
                if (user.getUsertype().equals(UserType.admin)) {
                    bookingDetailList = bookingDetailCrud.bookingDetailOfCurrentDateAndHigher();

                } else if (user.getUsertype().equals(UserType.futsalowner)) {
                    Futsal futsal = futsalCrud.checkIfFutsalRegistered(sessionUserId);
                    bookingDetailList = bookingDetailCrud.bookingDetailByFutsalOwner(futsal);
                } else {
                    bookingDetailList = bookingDetailCrud.bookingDetailOfCurrentDateAndHigherByUser(user);

                }
            }
        }
    }

    public Boolean checkPaymentStatus(BookingDetail bookingDetail) {
        if (bookingDetail.getPaymentstatus().equals("incomplete")) {
            return true;
        } else {
            return true;
        }
    }

    public void saveBookingDetail(BookingDetail bookingDetail) {
        if (bookingDetail != null) {
            this.bookingDetail = bookingDetail;
        }
    }

    public String getBookingAmount() {
        String amount = "";
        if (this.bookingDetail.getId() != null) {
            amount = String.valueOf(bookingDetail.getBookinginformation().getAmount());
        }
        return amount;
    }

}
