package com.saugat.services;

import Controller.PasswordHashController;
import Entities.User;
import Model.UserCrud;
import com.saugat.bean.enums.ActionType;
import com.saugat.bean.enums.ResourceType;
import com.saugat.interceptors.Acl;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

/**
 *
 * @author saugat
 */
@Path("/user")
@Produces("application/json")
public class UserService extends Application {

    @Inject
    private UserCrud userCrud;

    @Acl(actionName = ActionType.READ, resourceName = ResourceType.USER)
    @GET
    public Response getAllFutsal() {
        List<User> userList = userCrud.getAllData();
        List<User> modifiedUserList = new ArrayList<>();
        if (!userList.isEmpty()) {
            for (User user : userList) {
                user.setUserpassword("*******");
                modifiedUserList.add(user);
            }
            ResponseMessage responseMessage = new ResponseMessage("OK", "200", "Data Found Successfully",
                    modifiedUserList);
            return Response.ok(responseMessage).build();
        } else {
            ResponseMessage responseMessage = new ResponseMessage("NOT FOUND", "404", "Login Required", "");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(responseMessage)
                    .build();
        }
    }

    @Acl(actionName = ActionType.READ, resourceName = ResourceType.USER)
    @GET
    @Path("/{id}")
    public Response getDataById(@PathParam("id") Long id) {
        if (id != null) {
            User user = userCrud.getDataById(id);
            if (user != null) {
                user.setUserpassword("*******");
                ResponseMessage responseMessage = new ResponseMessage("OK", "200", "Data Found Successfully", user);
                return Response.ok(responseMessage).build();
            } else {

                ResponseMessage responseMessage = new ResponseMessage("NOT FOUND", "404", "Doesn't Exist", "");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(responseMessage)
                        .build();
            }
        } else {
            ResponseMessage responseMessage = new ResponseMessage("BAD REQUEST", "400", "BAD REQUEST", "");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(responseMessage)
                    .build();
        }
    }

    @Acl(actionName = ActionType.CREATE, resourceName = ResourceType.USER)
    @POST
    public Response create(User user) {
        if (user != null) {
            user.setUserpassword(new PasswordHashController().getPasswordHash(user.getUserpassword()));
            Boolean status = userCrud.save(user);
            if (status) {
                user.setUserpassword("********");
                ResponseMessage responseMessage = new ResponseMessage("OK", "200", "Created Successfully", user);
                return Response.ok(responseMessage).build();
            } else {
                ResponseMessage responseMessage = new ResponseMessage("NOT_ACCEPTABLE", "406", "NOT_ACCEPTABLE",
                        "There is a problem in the data sent.");
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity(responseMessage)
                        .build();
            }
        } else {
            ResponseMessage responseMessage = new ResponseMessage("EXPECTATION_FAILED", "400", "EXPECTATION_FAILED", "");
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(responseMessage)
                    .build();
        }
    }

    @Acl(actionName = ActionType.DELETE, resourceName = ResourceType.USER)
    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        if (id != null) {
            Boolean status = userCrud.deleteById(id);
            if (status) {
                ResponseMessage responseMessage = new ResponseMessage("NO CONTENT", "301", "Record Deleted", "");
                return Response.ok(responseMessage).build();
            } else {
                ResponseMessage responseMessage = new ResponseMessage("NOT FOUND", "404", "Not Found", "");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(responseMessage)
                        .build();
            }
        } else {
            ResponseMessage responseMessage = new ResponseMessage("BAD REQUEST", "400", "BAD REQUEST", "");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(responseMessage)
                    .build();
        }
    }

}
