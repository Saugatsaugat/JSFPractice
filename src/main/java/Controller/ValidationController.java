/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author saugat
 */
public class ValidationController<T> {

    public String checkEmail(String email) {
        String msg = null;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            msg = "Wrong Email Format";
        }
        return msg;
    }
    /////////////////////////////////////

    public String checkName(String name) {
        String msg = null;
        String nameRegex = "[A-Za-z]+";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            msg = "Name must be in alphabets";
        }
        return msg;
    }
    ///////////////////////////////////////////

    public String checkNumber(T mobile) {
        String msg = null;
        String numRegex = "[0-9]+";
        Pattern pattern = Pattern.compile(numRegex);
        Matcher matcher = pattern.matcher(mobile.toString());
        if (!matcher.matches()) {
            msg = "must be in integer";
        }
        return msg;
    }

    public List<String> validateUserRegistration(User registerInformation) {
        List<String> errorMessage = new ArrayList<>();

        String type = registerInformation.getUsertype().toString().toLowerCase();
        String firstname = registerInformation.getFirstname();
        String lastname = registerInformation.getLastname();
        String email = registerInformation.getEmail();
        String password = registerInformation.getUserpassword();
        Long mobile = registerInformation.getMobile();

        if ((firstname == null) || (lastname == null) || (email == null) || (password == null) || (mobile == null)) {
            errorMessage.add("Only midname can be null");
        }
        String msg1 = checkEmail(email);
        String msg2 = checkNumber((T) mobile);
        String msg3 = checkName(firstname);
        String msg4 = checkName(lastname);
        if (msg1 != null) {
            errorMessage.add(msg1);

        }
        if (msg2 != null) {
            errorMessage.add(msg2);
        }
        if ((msg3 != null) || (msg4 != null)) {
            errorMessage.add(msg3);
        }
        return errorMessage;

    }
//    public boolean checkIfEmailExist(String email) throws SQLException {
//        if(email!=null){
//        User userList = new UserCRUD(new User()).getEmailByData(email);
//        if(userList!=null){
//            return true;
//        }
//        else{
//                    return false;
//
//        }
//        }
//        else{
//            return false;
//        }
//    }
//    public boolean checkIfIdExistForUser(Long id) throws SQLException {
//        List<User> userList = new UserCRUD(new User()).getAllData();
//        for (User user : userList) {
//            if (user.getId().equals(id)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    public boolean checkIfIdExistForFutsal(long id) throws SQLException {
//        List<Futsal> futsalList = new FutsalCRUD(new Futsal()).getAllData();
//        for (Futsal futsal : futsalList) {
//            if (futsal.getId().equals(id)) {
//                return true;
//            }
//        }
//        return false;
//    }

}
