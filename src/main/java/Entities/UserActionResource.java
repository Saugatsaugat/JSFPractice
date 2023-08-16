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
@Table(name = "user_action_resource")
public abstract class UserActionResource extends AbstractEntity<AclAction> implements IAbstractEntity, Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "acl_action_id")
    private AclAction aclAction;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    private Boolean isAllowed;

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

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Boolean getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Boolean isAllowed) {
        this.isAllowed = isAllowed;
    }

}
