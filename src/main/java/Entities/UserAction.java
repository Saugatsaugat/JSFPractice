package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "user_action")
public class UserAction extends AbstractEntity<UserAction> implements IAbstractEntity, Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "acl_action_id")
    private AclAction aclAction;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AclAction getAclAction() {
        return aclAction;
    }

    public void setAclAction(AclAction aclAction) {
        this.aclAction = aclAction;
    }

    @Override
    public String getTableName() {
        return "UserAction";
    }

}
