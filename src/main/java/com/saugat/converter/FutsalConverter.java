
package com.saugat.converter;

import Entities.Futsal;
import Entities.User;
import Model.FutsalCrud;
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
@FacesConverter(value = "futsalconverter")
public class FutsalConverter implements Converter {

    @Inject
    private FutsalCrud futsalCrud;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        Futsal selectedFutsal = null;
        try {
            Long userId = Long.valueOf(value);
            selectedFutsal = futsalCrud.getDataById(userId);
            return selectedFutsal;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return selectedFutsal;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Futsal) {
            Futsal user = (Futsal) value;
            return String.valueOf(user.getId());
        }
        return null;
    }
}
