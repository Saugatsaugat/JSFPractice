package com.saugat.services;

import Controller.PasswordHashController;
import Entities.User;
import Model.UserCrud;
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
    
    private ResponseMessage responseMessage = new ResponseMessage();
    
    @GET
    public Response getAllFutsal() {
        List<User> userList = userCrud.getAllData();
        if (!userList.isEmpty()) {
            responseMessage.setCode("200");
            responseMessage.setMessage("Data Found Successfully");
            responseMessage.setStatus("Ok");
            responseMessage.setResult(userList);
            return Response.ok(responseMessage).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getDataById(@PathParam("id") Long id) {
        if (id != null) {
            User user = userCrud.getDataById(id);
            if (user != null) {
                responseMessage = new ResponseMessage();
                responseMessage.setCode("200");
                responseMessage.setMessage("Record Found");
                responseMessage.setStatus("Ok");
                responseMessage.setResult(user);
                return Response.ok(responseMessage).build();
            } else {
                
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    public Response create(User user) {
        if (user != null) {
            user.setUserpassword(new PasswordHashController().getPasswordHash(user.getUserpassword()));
            Boolean status = userCrud.save(user);
            if (status) {
                responseMessage = new ResponseMessage();
                responseMessage.setCode("200");
                responseMessage.setMessage("Record Added");
                responseMessage.setStatus("Ok");
                responseMessage.setResult(user);
                return Response.ok(responseMessage).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        } else {
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        if (id != null) {
            Boolean status = userCrud.deleteById(id);
            if (status) {
                responseMessage = new ResponseMessage();
                responseMessage.setCode("301");
                responseMessage.setMessage("Record Deleted");
                responseMessage.setStatus("No Content");
                responseMessage.setResult("");
                return Response.ok(responseMessage).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
}
