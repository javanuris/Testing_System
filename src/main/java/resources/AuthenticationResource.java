package resources;

import entity.Credentials;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/authentication")
public class AuthenticationResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Credentials getCredential(){
        Credentials credentials = new Credentials();
        credentials.setPassword("root");
        credentials.setUsername("root");
        return credentials;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response authenticateUser(Credentials credentials) {

        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try {
            authenticate(username, password);
            String token = issueToken(username);
            return Response.ok(token).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        if ("root".equals(username) && "root".equals(password)) {
            return;
        } else {
            throw new Exception();
        }
    }

    private String issueToken(String username) {
        return "nuris";
    }


}
