package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import com.saugat.bean.enums.ActionType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * user, action, userAction, Resource, userActionResource
 *
 * @author saugat
 */
@Entity
@Table(name = "acl_action")
public class AclAction extends AbstractEntity<AclAction> implements IAbstractEntity, Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "action_name",unique = true)
    private ActionType actionName;

    public ActionType getActionName() {
        return actionName;
    }

    public void setActionName(ActionType actionName) {
        this.actionName = actionName;
    }

    @Override
    public String getTableName() {
        return "AclAction";
    }

}
