package com.saugat.converter;

import Entities.User;
import Model.UserCrud;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author saugat
 */
@FacesConverter(value = "userconverter")
public class UserConverter implements Converter {

    @Inject
    private UserCrud userCrud;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        User selectedUser = null;
        try {
            Long userId = Long.valueOf(value);
            selectedUser = userCrud.getDataById(userId);
            return selectedUser;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return selectedUser;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof User) {
            User user = (User) value;
            return String.valueOf(user.getId());
        }
        return null;
    }
}
