package com.saugat.converter;

import Entities.User;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author saugat
 */
@FacesConverter(value = "userconverter")
public class UserConverter extends AbstractEntityConverter<User> {

    public UserConverter() {
        super(User.class);
    }
}
