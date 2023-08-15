package com.saugat.services;

import Entities.User;
import Model.UserCrud;
import com.saugat.beans.LoginRequest;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
                String token = TokenManager.generateToken(user.getId().toString());

                return Response.ok(token).build();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/tokenVerification/{string}")
    public Response checkJWT(@PathParam("string") String token) {
        responseMessage = new ResponseMessage();

        if (!token.isEmpty()) {
            String userId = TokenManager.verifyToken(token);
            if (userId != null) {
              
                return Response.ok(userId).build();

            } else {
                return Response.status(Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

}
