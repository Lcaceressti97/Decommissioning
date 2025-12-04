package hn.com.tigo.tool.annotations.decommissioning.module;

import javax.annotation.Priority;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import hn.com.tigo.tool.annotations.decommissioning.api.Log;

/**
 *
 * @author Laurent G. Cáceres
 */

@Provider
@Priority(value = 1)
public class ResourceFilterLog implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {

		if (resourceInfo.getResourceMethod().isAnnotationPresent(Log.class)) {
			context.register(LogRequestInterceptor.class);
		}
	}
}
