package Controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@SessionScoped
public class KhaltiVerificationController implements Serializable {

    public Boolean verifyKhaltiTransaction(String idx, String token, float amount) {

        String url = "https://khalti.com/api/v2/payment/verify/";
        String response = "";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Key test_public_key_40718b41656a4e2aaf9624d9e3137e46");
            

            // Enable input/output streams
            con.setDoOutput(true);

            // Prepare the payload
            String payload = "token=" + token + "&amount=" + amount;

            // Write payload to the request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(payload);
            wr.flush();
            wr.close();

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder responseBuilder = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }
            in.close();

            response = responseBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
