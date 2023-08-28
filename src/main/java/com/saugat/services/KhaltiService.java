package com.saugat.services;

import Controller.KhaltiVerificationController;
import javax.json.JsonArray;
import javax.json.JsonObject;
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
@Path("/khaltiPay")
@Produces(MediaType.APPLICATION_JSON)
public class KhaltiService extends Application {

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response verification(JsonObject payload) {
        
        String idx = payload.getJsonString("idx").getString();
        String token = payload.getJsonString("token").getString();
        //this amount is in paisa.
        float amount = payload.getJsonNumber("amount").longValue(); 
        Boolean status = new KhaltiVerificationController().verifyKhaltiTransaction(idx, token, amount);
        
        
        
        
        
        return Response.ok().build();
    }
}
