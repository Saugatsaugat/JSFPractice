/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 *
 * @author saugat
 */
public class PasswordHashController {

    public String getPasswordHash(String password) {
        MessageDigest messageDigest;
        String encodedPassword = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
           String stringHash = new String(messageDigest.digest());

            byte[] utf8Bytes = stringHash.getBytes(StandardCharsets.UTF_8);
            encodedPassword = new String(utf8Bytes, StandardCharsets.UTF_8);
            
            return encodedPassword;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return encodedPassword;

    }

}
