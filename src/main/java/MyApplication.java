import filters.AccessControlResponseFilter;
import resources.AuthenticationResource;
import filters.SecurityFilter;
import resources.RegistrationResource;
import resources.TestResource;
import resources.UserResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/")
//The java class declares root resource and provider classes
public class MyApplication extends Application {
    //The method returns a non-empty collection with classes, that must be included in the published JAX-RS application
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(TestResource.class);
        h.add(RegistrationResource.class);
        h.add(UserResource.class);

        h.add(SecurityFilter.class);
        h.add(AuthenticationResource.class);
        h.add(AccessControlResponseFilter.class);
        return h;
    }
}