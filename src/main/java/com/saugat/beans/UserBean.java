package com.saugat.beans;

import Entities.User;
import com.saugat.bean.enums.UserType;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@SessionScoped
public class UserBean implements Serializable {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getIsAdmin() {
        return user != null && user.getUsertype().equals(UserType.admin);

    }

    public boolean getIsFutsalOwner() {
        return user != null && user.getUsertype().equals(UserType.futsalowner);
    }

    public boolean getIsUser() {
        return user != null && user.getUsertype().equals(UserType.user);
    }
}
