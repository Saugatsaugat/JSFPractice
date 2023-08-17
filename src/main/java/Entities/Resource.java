package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import com.saugat.bean.enums.ResourceType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "resource")
public class Resource extends AbstractEntity<Resource> implements IAbstractEntity, Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_name",unique = true)
    private ResourceType resourceName;

    public ResourceType getResourceName() {
        return resourceName;
    }

    public void setResourceName(ResourceType resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String getTableName() {
        return "Resource";
    }

}
