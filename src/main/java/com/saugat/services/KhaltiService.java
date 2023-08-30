package com.saugat.services;

import Controller.KhaltiVerificationController;
import Entities.BookingDetail;
import Entities.Payment;
import Entities.User;
import Model.BookingDetailCrud;
import Model.PaymentCrud;
import com.saugat.bean.enums.WalletType;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author saugat
 */
@Path("/khaltiPay")
@Produces(MediaType.APPLICATION_JSON)
public class KhaltiService extends Application {

    @Inject
    private BookingDetailCrud bookingDetailCrud;
    @Inject
    private PaymentCrud paymentCrud;

    @Inject
    private KhaltiVerificationController khaltiVerificationController;

    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response verification(JsonObject payload, @PathParam("id") Long id) throws IOException {

        String idx = payload.getJsonString("idx").getString();
        String token = payload.getJsonString("token").getString();

        //this amount is in paisa.
        float amount = payload.getJsonNumber("amount").longValue();
        BookingDetail bookingDetail = bookingDetailCrud.getDataById(id);

        Boolean status = khaltiVerificationController.verifyKhaltiTransaction(idx, token, amount, bookingDetail);
        if (status) {
            ResponseMessage responseMessage = new ResponseMessage("SUCCESS","200","Complete","Complete");
            return Response.ok(responseMessage).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    public Boolean savePaymentInformation(Map<String, String> result, BookingDetail bookingDetail) {
        String typeIdx = result.get("typeIdx");
        String successIdx = result.get("successIdx");
        String username = result.get("username");
        String merchantIdx = result.get("merchantIdx");

        User user = bookingDetail.getBookinginformation().getUser();
        if (user.getId() != null) {
            Payment payment = new Payment();
            payment.setWalletType(WalletType.KHALTI);
            payment.setSuccessId(successIdx);
            payment.setKhaltiTypeIdx(typeIdx);
            payment.setKhaltiMerchantIdx(merchantIdx);
            payment.setClientNameReceivedFromKhalti(username);
            payment.setTransactionDate(new Date());
            payment.setUser(user);
            payment.setBookingDetail(bookingDetail);
            if (paymentCrud.save(payment)) {
                bookingDetail.setPaymentstatus("completed");
                if (bookingDetailCrud.update(bookingDetail, bookingDetail.getId())) {
                    return true;
                }
            }

        }
        return false;
    }

}
