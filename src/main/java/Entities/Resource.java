package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "resource")
public class Resource extends AbstractEntity<Resource> implements IAbstractEntity, Serializable {

    private String resourceName;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String getTableName() {
        return "Resource";
    }

}
