package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import java.io.Serializable;
import javax.persistence.Column;
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
public class UserActionResource extends AbstractEntity<AclAction> implements IAbstractEntity, Serializable {

    @Column(name = "user_type")
    private String userType;

    @ManyToOne
    @JoinColumn(name = "acl_action_id")
    private AclAction aclAction;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    private Boolean isAllowed;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    @Override
    public String getTableName() {
        return "UserActionResource";
    }

}
