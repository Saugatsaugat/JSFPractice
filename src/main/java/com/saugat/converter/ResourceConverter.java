package com.saugat.converter;

import Entities.Resource;
import Model.AbstractCrud;
import Model.ResourceCrud;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author saugat
 */
@FacesConverter(value = "resourceconverter")
public class ResourceConverter extends AbstractEntityConverter {

    @Inject
    private ResourceCrud repo;

    @Override
    protected AbstractCrud getRepo() {
        return repo;
    }

    public ResourceConverter() {
        super(Resource.class);
    }
}
