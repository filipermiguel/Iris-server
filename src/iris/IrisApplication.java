package iris;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import iris.filter.CORSResponseFilter;

@ApplicationPath("/")
public class IrisApplication extends ResourceConfig {

	public IrisApplication() {
		super(MultiPartFeature.class, JacksonFeature.class);
		 register(CORSResponseFilter.class);
	}

}
