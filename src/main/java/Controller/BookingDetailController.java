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
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
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
    private String updatePaymentSuccessStatus;

    public String getUpdatePaymentSuccessStatus() {
        return updatePaymentSuccessStatus;
    }

    public void setUpdatePaymentSuccessStatus(String updatePaymentSuccessStatus) {
        this.updatePaymentSuccessStatus = updatePaymentSuccessStatus;
    }

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
        updatePaymentSuccessStatus = "";
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
        if (bookingDetail.getPaymentstatus().equals("completed")) {
            return false;
        } else {
            return true;
        }
    }

    public void saveBookingDetail(BookingDetail bookingDetail) {
        if (bookingDetail != null) {
            this.bookingDetail = bookingDetail;
        }
    }

    public Long getSelectedBookingInformationId() {
        return bookingDetail.getId();
    }

    public float getBookingAmount() {
        float amount = 0L;
        if (this.bookingDetail.getId() != null) {
            amount = bookingDetail.getBookinginformation().getAmount();
        }
        return amount;
    }

    public void displaySuccessMessage() {
        if (updatePaymentSuccessStatus.equals("true")) {
            updatePaymentSuccessStatus = "";
            ValidationMessageGenerationUtil.validationMessageGeneration("Payment Success", "informational");
        } else if (updatePaymentSuccessStatus.equals("false")) {
            updatePaymentSuccessStatus = "";
            ValidationMessageGenerationUtil.validationMessageGeneration("Payment Failed", "error");
        } else {

        }
    }

}
