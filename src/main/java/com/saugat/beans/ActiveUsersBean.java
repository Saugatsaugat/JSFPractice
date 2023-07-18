package com.saugat.beans;

import Entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@ApplicationScoped
public class ActiveUsersBean {

    private List<User> activeUsersList;

    public ActiveUsersBean() {
        activeUsersList = new ArrayList<>();
    }

    public List<User> getActiveUsersList() {
        return activeUsersList;
    }

    public void incrementActiveUsersList(User user) {
        activeUsersList.add(user);
    }

    public void decrementActiveUsersList(User user) {
        if (activeUsersList.contains(user)) {
            activeUsersList.remove(user);
        }
    }

    public int getCount() {
        return activeUsersList.size();
    }

}
