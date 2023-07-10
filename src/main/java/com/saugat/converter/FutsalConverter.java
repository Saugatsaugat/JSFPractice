
package com.saugat.converter;

import Entities.Futsal;
import Model.AbstractCrud;
import Model.FutsalCrud;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author saugat
 */
@FacesConverter(value = "futsalconverter")
public class FutsalConverter extends AbstractEntityConverter {
    
    @Inject
    private FutsalCrud repo;
    @Override
    protected AbstractCrud getRepo(){
        return repo;
    }
    
    public FutsalConverter(){
        super(Futsal.class);
    }
 
}
