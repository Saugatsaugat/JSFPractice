package Controller;

import Entities.BookingDetail;
import Entities.Payment;
import Entities.User;
import Model.BookingDetailCrud;
import Model.PaymentCrud;
import Model.UserCrud;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saugat.bean.enums.WalletType;
import com.saugat.beans.UserBean;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@RequestScoped
public class KhaltiVerificationController implements Serializable {

    @Inject
    private UserBean userBean;
    @Inject
    private PaymentCrud paymentCrud;
    @Inject
    private BookingDetailCrud bookingDetailCrud;
    @Inject
    private UserCrud userCrud;

    public Boolean verifyKhaltiTransaction(String idx, String token, float amount, BookingDetail bookingDetail) {
        Map<String, String> result = new HashMap<>();
        StringBuilder response = new StringBuilder();

        try {
            String postData = "token=" + token + "&amount=" + amount;

            URL url = new URL("https://khalti.com/api/v2/payment/verify/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization",
                    "Key test_secret_key_564f0fa350ab43aa890419a00989a27c");

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(postData);
            wr.close();

            // Get the response Code
            int responseCode = connection.getResponseCode();

            //Get Response  
            try {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();

                // json transformation using fasterxml jackson
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response.toString());
                    String completed = jsonNode.get("state").get("name").asText();

                    if (completed != null) {
                        String successIdx = "";
                        String typeIdx = "";
                        String username = "";
                        String merchantIdx = "";

                        typeIdx = jsonNode.get("type").get("idx").asText();
                        successIdx = jsonNode.get("state").get("idx").asText();
                        username = jsonNode.get("user").get("name").asText();
                        merchantIdx = jsonNode.get("merchant").get("idx").asText();

                        result.put("typeIdx", typeIdx);
                        result.put("successIdx", successIdx);
                        result.put("username", username);
                        result.put("merchantIdx", merchantIdx);
                        Boolean stat = savePaymentInformation(typeIdx, successIdx, username,
                                merchantIdx, bookingDetail);
                        if (stat) {
                            return true;
                        }

                    }
                } catch (Exception e) {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean savePaymentInformation(String typeIdx, String successIdx, String username,
            String merchantId, BookingDetail bookingDetail) {
        User user = bookingDetail.getBookinginformation().getUser();
        if (user.getId() != null) {
            Payment payment = new Payment();
            payment.setWalletType(WalletType.KHALTI);
            payment.setSuccessId(successIdx);
            payment.setKhaltiTypeIdx(typeIdx);
            payment.setKhaltiMerchantIdx(merchantId);
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
