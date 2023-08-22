package com.saugat.services;

import Entities.Futsal;
import Model.FutsalCrud;
import com.saugat.bean.enums.ActionType;
import com.saugat.bean.enums.ResourceType;
import com.saugat.interceptors.Acl;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author saugat
 */
@Path("/futsal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class futsalService extends Application {

    @Inject
    private FutsalCrud futsalCrud;

    @Acl(actionName = ActionType.READ, resourceName = ResourceType.FUTSAL)
    @GET
    public Response getAllFutsal() {
        List<Futsal> futsalList = futsalCrud.getAllData();
        if (!futsalList.isEmpty()) {
            ResponseMessage responseMessage = new ResponseMessage("OK", "200", "Data Found Successfully",
                    futsalList);
            return Response.ok(responseMessage).build();
        } else {
            ResponseMessage responseMessage = new ResponseMessage("NOT FOUND", "404", "Login Required", "");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(responseMessage)
                    .build();
        }
    }

    @Acl(actionName = ActionType.READ, resourceName = ResourceType.FUTSAL)
    @GET
    @Path("/{id}")
    public Response getDataById(@PathParam("id") Long id) {
        if (id != null) {
            Futsal futsal = futsalCrud.getDataById(id);
            if (futsal != null) {
                ResponseMessage responseMessage = new ResponseMessage("OK", "200", "Data Found Successfully", futsal);

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

    @Acl(actionName = ActionType.CREATE, resourceName = ResourceType.FUTSAL)
    @POST
    public Response create(Futsal futsal) {
        if (futsal != null) {
            Boolean status = futsalCrud.save(futsal);
            if (status) {
                ResponseMessage responseMessage = new ResponseMessage("OK", "200",
                        "Data Created Successfully", futsal);

                return Response.ok(responseMessage).build();
            } else {
                ResponseMessage responseMessage = new ResponseMessage("NOT_ACCEPTABLE", "406",
                        "NOT_ACCEPTABLE",
                        "There is a problem in the data sent.");
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity(responseMessage)
                        .build();
            }
        } else {
            ResponseMessage responseMessage = new ResponseMessage("EXPECTATION_FAILED", "400",
                    "EXPECTATION_FAILED", "");
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(responseMessage)
                    .build();
        }
    }

    @Acl(actionName = ActionType.DELETE, resourceName = ResourceType.FUTSAL)
    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        if (id != null) {
            Boolean status = futsalCrud.deleteById(id);
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
