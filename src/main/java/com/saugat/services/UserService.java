package com.saugat.services;

import Controller.PasswordHashController;
import Entities.User;
import Model.UserCrud;
import com.saugat.bean.enums.UserType;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

    @GET
    public Response getAllUsers() {
        return Response.ok(userCrud.getAllData()).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        User user = userCrud.getDataById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes("application/json")
    public Response addUser(User user) {
        try {
            if (user != null) {
                user.setUserpassword(new PasswordHashController().getPasswordHash(
                        user.getUserpassword()));
                if (user.getUsertype() == null) {
                    user.setUsertype(UserType.user);
                }
                if (userCrud.save(user)) {
                    return Response.ok(user).build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } catch (Exception e) {

        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({"application/json","text/plain"})
    public Response updateUser(@PathParam("id") Long id, User user) {
        if (user!= null) {
            
            if (userCrud.update(user, id)) {
                return Response.ok(user).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();

            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        if (id != null) {
            userCrud.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
