package com.saugat.services;

import Entities.User;
import Model.UserCrud;
import com.saugat.beans.LoginRequest;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @POST
    public Response checkLogin(LoginRequest loginRequest) {
        if (loginRequest != null) {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            User user = userCrud.findByUsernameAndPassword(username, password);
            if (user != null) {

                String token = TokenManager.generateToken(user.getId().toString());

                return Response.ok(token).build();
            } else {
                ResponseMessage responseMessage = new ResponseMessage("NOT FOUND", "404", "Login Required", "");
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

//    @POST
//    @Path("/tokenVerification/{string}")
//    public Response checkJWT(@PathParam("string") String token) {
//        responseMessage = new ResponseMessage();
//
//        if (!token.isEmpty()) {
//            String userId = TokenManager.verifyToken(token);
//            if (userId != null) {
//              
//                return Response.ok(userId).build();
//
//            } else {
//                return Response.status(Status.UNAUTHORIZED).build();
//            }
//        } else {
//            return Response.status(Status.UNAUTHORIZED).build();
//        }
//    }
}
