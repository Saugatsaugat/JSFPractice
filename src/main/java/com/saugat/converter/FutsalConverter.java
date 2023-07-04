
package com.saugat.converter;

import Entities.Futsal;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author saugat
 */
@FacesConverter(value = "futsalconverter")
public class FutsalConverter extends AbstractEntityConverter {
    
    public FutsalConverter(){
        super(Futsal.class);
    }
 
}
