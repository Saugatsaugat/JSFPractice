package com.saugat.converter;

import Model.AbstractCrud;
import Model.AbstractEntity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**

 * @author saugat
 * @param <T>
 */

public abstract class AbstractEntityConverter<T extends AbstractEntity> implements Converter {

    private final Class<T> entityClass;

    public AbstractEntityConverter(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    protected abstract AbstractCrud getRepo();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        T selectedEntity = null;
        try {
            selectedEntity = entityClass.newInstance();
            selectedEntity.setId(Long.valueOf(value));
        } catch (Exception e) {
        }
        return selectedEntity;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof AbstractEntity) {
            AbstractEntity entity = (AbstractEntity) value;
            return String.valueOf(entity.getId());
        }
        return null;
    }

}
