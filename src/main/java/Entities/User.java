package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import com.saugat.bean.enums.UserType;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "User")
public class User extends AbstractEntity<User> implements IAbstractEntity, Serializable {

    @Enumerated(EnumType.STRING)
    private UserType usertype;
    private String email;
    private String firstname;
    private String midname;
    private String lastname;
    private Long mobile;
    private String userpassword;

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Override
    public String getTableName() {
        return "User";
    }

}
