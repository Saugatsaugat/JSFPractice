package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * user, action, userAction, Resource, userActionResource
 *
 * @author saugat
 */
@Entity
@Table(name = "acl_action")
public abstract class AclAction extends AbstractEntity<AclAction> implements IAbstractEntity, Serializable {

    private String actionName;

    public String getActionName() {
        return actionName;
    }
    
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

}
