package nl.hu.setup;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("restservices")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("nl.hu.dungeonsanddata.webservices", "nl.hu.dungeonsanddata.security");
        register(JacksonFeature.class);
        register(RolesAllowedDynamicFeature.class);
    }
}
