import filters.AccessControlResponseFilter;
import filters.AuthenticationFilter;
import resources.AuthenticationEndpointResource;
import filters.SecurityFilter;
import resources.SecureResource;
import resources.TestResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 13.07.2017.
 */
@ApplicationPath("/")
//The java class declares root resource and provider classes
public class MyApplication extends Application {
    //The method returns a non-empty collection with classes, that must be included in the published JAX-RS application
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(TestResource.class);
        h.add(SecureResource.class);
        h.add(SecurityFilter.class);
        h.add(AuthenticationFilter.class);
        h.add(AuthenticationEndpointResource.class);
        h.add(AccessControlResponseFilter.class);


        return h;
    }
}