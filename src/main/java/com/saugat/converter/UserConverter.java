package com.saugat.converter;

import Entities.User;
import Model.AbstractCrud;
import Model.UserCrud;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author saugat
 */
@FacesConverter(value = "userconverter")
public class UserConverter extends AbstractEntityConverter<User> {

    @Inject
    private UserCrud repo;

    @Override
    protected AbstractCrud getRepo() {
        return repo;
    }

    public UserConverter() {
        super(User.class);
    }
}
