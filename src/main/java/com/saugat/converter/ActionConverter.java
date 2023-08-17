package com.saugat.converter;

import Entities.AclAction;
import Entities.Futsal;
import Model.AbstractCrud;
import Model.AclActionCrud;
import Model.FutsalCrud;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author saugat
 */
@FacesConverter(value = "actionconverter")
public class ActionConverter extends AbstractEntityConverter {

    @Inject
    private AclActionCrud repo;

    @Override
    protected AbstractCrud getRepo() {
        return repo;
    }

    public ActionConverter() {
        super(AclAction.class);
    }
}
