package Controller;

import Entities.AclAction;
import Entities.Resource;
import Entities.User;
import Entities.UserActionResource;
import Model.AclActionCrud;
import Model.ResourceCrud;
import Model.UserActionResourceCrud;
import Model.UserCrud;
import com.saugat.bean.enums.UserType;
import com.saugat.messageGeneration.ValidationMessageGenerationUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class AclController implements Serializable {

    @Inject
    private UserCrud userCrud;
    @Inject
    private ResourceCrud resourceCrud;
    @Inject
    private AclActionCrud aclActionCrud;
    @Inject
    private UserActionResourceCrud userActionResourceCrud;

    private UserType userType;
    private UserActionResource userActionResource;
    private User user;
    private Resource resource;
    private AclAction action;
    private Boolean allowAcl;
    private List<User> userList;
    private List<Resource> resourceList;
    private List<AclAction> actionList;
    private List<UserActionResource> aclList;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<UserActionResource> getAclList() {
        return aclList;
    }

    public void setAclList(List<UserActionResource> aclList) {
        this.aclList = aclList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<AclAction> getActionList() {
        return actionList;
    }

    public void setActionList(List<AclAction> actionList) {
        this.actionList = actionList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public AclAction getAction() {
        return action;
    }

    public void setAction(AclAction action) {
        this.action = action;
    }

    public Boolean getAllow() {
        return allowAcl;
    }

    public void setAllow(Boolean allowAcl) {
        this.allowAcl = allowAcl;
    }

    public UserType[] getUserTypes() {
        return UserType.values();
    }

    @PostConstruct
    public void init() {
        allowAcl = false;
        user = new User();
        resource = new Resource();
        action = new AclAction();
        userList = userCrud.getAllData();
        resourceList = resourceCrud.getAllData();
        actionList = aclActionCrud.getAllData();
        userActionResource = new UserActionResource();
        aclList = userActionResourceCrud.getAllData();
    }

    public void afterAdd() {
        userActionResource = new UserActionResource();
        userType = null;
        resource = new Resource();
        action = new AclAction();
        allowAcl = false;
    }

    public void updateAcl(UserActionResource item) {
        userActionResource = item;
        if (item != null) {
            this.resource = item.getResource();
            this.userType = item.getUserType();
            this.allowAcl = item.getIsAllowed();
            this.action = item.getAclAction();
        }
    }

    public void saveAclData() {
        if ((this.userType != null) && (this.resource.getId() != null)
                && (this.action.getId() != null)) {
            resource = resourceCrud.getDataById(resource.getId());
            action = aclActionCrud.getDataById(action.getId());
            Boolean status = userActionResourceCrud.checkIfExistsByAclDetails(
                    resource, userType,
                    action, allowAcl);
            if (!status) {
                userActionResource.setAclAction(action);
                userActionResource.setUserType(userType);
                userActionResource.setResource(resource);
                userActionResource.setIsAllowed(allowAcl);
                if (userActionResourceCrud.save(userActionResource)) {
                    ValidationMessageGenerationUtil.validationMessageGeneration("ACL Added",
                            "informational");
                    afterAdd();
                    aclList = userActionResourceCrud.getAllData();
                    return;
                } else {
                    ValidationMessageGenerationUtil.validationMessageGeneration("ACL Add Failed",
                            "error");
                    return;
                }
            } else {
                ValidationMessageGenerationUtil.validationMessageGeneration("ACL Already Added",
                        "informational");
                return;
            }
        }
        afterAdd();

    }

    public void updateAcl() {
        Boolean status = userActionResourceCrud.checkIfExits(userActionResource);
        if (status) {
            this.userActionResource.setResource(resource);
            this.userActionResource.setAclAction(action);
            this.userActionResource.setIsAllowed(allowAcl);
            this.userActionResource.setUserType(userType);

            if (userActionResourceCrud.update(userActionResource, userActionResource.getId())) {
                ValidationMessageGenerationUtil.validationMessageGeneration(
                        "Acl Updated", "informational");
                return;
            } else {
                ValidationMessageGenerationUtil.validationMessageGeneration(
                        "Acl Update Failed", "error");
                return;
            }
        } else {
            ValidationMessageGenerationUtil.validationMessageGeneration(
                    "Acl Does not Exist", "error");
            return;
        }
    }

    public void deleteAcl() {
        if (this.userActionResource.getId() != null) {
            Boolean status = userActionResourceCrud.checkIfExits(userActionResource);
            if (status) {
                if (userActionResourceCrud.deleteById(userActionResource.getId())) {
                    ValidationMessageGenerationUtil.validationMessageGeneration(
                            "Acl Deleted", "informational");
                    aclList = userActionResourceCrud.getAllData();
                    return;
                } else {
                    ValidationMessageGenerationUtil.validationMessageGeneration(
                            "Acl Deletion Failed", "error");
                    return;
                }
            }
        }
    }
}
