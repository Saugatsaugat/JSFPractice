package com.saugat.services;

import Entities.User;
import Model.UserCrud;
import com.saugat.beans.LoginRequest;
import com.saugat.beans.UserBean;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author saugat
 */
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginService extends Application {

    @Inject
    private UserCrud userCrud;

    ResponseMessage responseMessage = new ResponseMessage();

    @POST
    public Response checkLogin(LoginRequest loginRequest) {
        responseMessage = new ResponseMessage();
        if (loginRequest != null) {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            User user = userCrud.findByUsernameAndPassword(username, password);
            if (user != null) {
                responseMessage.setCode("200");
                responseMessage.setStatus("Ok");
                responseMessage.setMessage("User Exists");
                responseMessage.setResult(user);
                return Response.ok(responseMessage).build();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
