package Controller;

import Entities.AclAction;
import Entities.Resource;
import Entities.User;
import Entities.UserActionResource;
import Model.AclActionCrud;
import Model.ResourceCrud;
import Model.UserActionResourceCrud;
import Model.UserCrud;
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

    private UserActionResource userActionResource;
    private User user;
    private Resource resource;
    private AclAction action;
    private Boolean allow;
    private List<User> userList;
    private List<Resource> resourceList;
    private List<AclAction> actionList;

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
        return allow;
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }

    @PostConstruct
    public void init() {
        user = new User();
        resource = new Resource();
        action = new AclAction();
        userList = userCrud.getAllData();
        resourceList = resourceCrud.getAllData();
        actionList = aclActionCrud.getAllData();
        this.setAllow(Boolean.FALSE);
        userActionResource = new UserActionResource();
    }

    public void afterAdd() {
        user = new User();
        resource = new Resource();
        action = new AclAction();
    }

    public void saveAcl() {
        if ((this.user.getId() != null) && (this.resource.getId() != null)
                && (this.action.getId() != null)) {
            this.user = userCrud.getDataById(user.getId());
            this.resource = resourceCrud.getDataById(resource.getId());
            this.action = aclActionCrud.getDataById(action.getId());

            Boolean status = userActionResourceCrud.checkIfExistsByAclDetails(
                    resource.getResourceName().toString(), user.getUsertype().toString(),
                    action.getActionName().toString());
            if (!status) {
                userActionResource.setAclAction(action);
                userActionResource.setUserType(user.getUsertype());
                userActionResource.setResource(resource);
                userActionResource.setIsAllowed(allow);
                if (userActionResourceCrud.save(userActionResource)) {
                    ValidationMessageGenerationUtil.validationMessageGeneration("ACL Added",
                            "informational");
                    afterAdd();
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
    }

}
