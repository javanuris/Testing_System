package resources;


import json.entity.RegistrationJson;
import services.UserService;
import services.exceptions.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/registration")
public class RegistrationResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(RegistrationJson registrationJson) {
        UserService userService = new UserService();
        try {
            userService.registrationUser(registrationJson);
        } catch (ServiceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
           return Response.ok("Registration has successfully completed").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RegistrationJson getUser() {
       RegistrationJson registrationJson = new RegistrationJson();
        registrationJson.setPhone("87772136654");
        registrationJson.setPassword("123456");
        registrationJson.setRepeatPassword("123456");
        registrationJson.setRoleId(1);
        return registrationJson;
    }

}
