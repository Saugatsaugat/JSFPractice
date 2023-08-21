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
import javax.ws.rs.core.Response.Status;

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

    private ResponseMessage responseMessage = new ResponseMessage();

    @Acl(actionName = ActionType.READ,resourceName = ResourceType.FUTSAL)
    @GET
    public Response getAllFutsal() {
        List<Futsal> futsalList = futsalCrud.getAllData();
        if (!futsalList.isEmpty()) {
            responseMessage.setCode("200");
            responseMessage.setMessage("Data Found Successfully");
            responseMessage.setStatus("Ok");
            responseMessage.setResult(futsalList);
            return Response.ok(responseMessage).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getDataById(@PathParam("id") Long id) {
        if (id != null) {
            Futsal futsal = futsalCrud.getDataById(id);
            if (futsal != null) {
                responseMessage = new ResponseMessage();
                responseMessage.setCode("200");
                responseMessage.setMessage("Record Found");
                responseMessage.setStatus("Ok");
                responseMessage.setResult(futsal);
                return Response.ok(responseMessage).build();
            } else {

                return Response.status(Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @Acl(actionName = ActionType.CREATE, resourceName = ResourceType.FUTSAL)
    @POST
    public Response create(Futsal futsal) {
        if (futsal != null) {
            Boolean status = futsalCrud.save(futsal);
            if (status) {
                responseMessage = new ResponseMessage();
                responseMessage.setCode("200");
                responseMessage.setMessage("Record Added");
                responseMessage.setStatus("Ok");
                responseMessage.setResult(futsal);
                return Response.ok(responseMessage).build();
            } else {
                return Response.status(Status.NOT_ACCEPTABLE).build();
            }
        } else {
            return Response.status(Status.EXPECTATION_FAILED).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        if (id != null) {
            Boolean status = futsalCrud.deleteById(id);
            if (status) {
                responseMessage = new ResponseMessage();
                responseMessage.setCode("301");
                responseMessage.setMessage("Record Deleted");
                responseMessage.setStatus("No Content");
                responseMessage.setResult("");
                return Response.ok(responseMessage).build();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

}
