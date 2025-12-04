package hn.com.tigo.tool.annotations.decommissioning;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hn.com.tigo.tool.annotations.decommissioning.module.LogRequestInterceptor;
import hn.com.tigo.tool.annotations.decommissioning.tool.SingletonDecommissioning;
import hn.com.tigo.tool.annotations.services.decommissioning.interfaces.IConfigLogAnnotationService;
import hn.com.tigo.tool.annotations.services.decommissioning.interfaces.ILogService;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	private final ILogService logService;
	private final IConfigLogAnnotationService configLogAnnotationService;
	private final SingletonDecommissioning sLDecommissioning;

	public WebConfigurer(ILogService logService, IConfigLogAnnotationService configLogAnnotationService,
			SingletonDecommissioning sLDecommissioning) {
		this.logService = logService;
		this.configLogAnnotationService = configLogAnnotationService;
		this.sLDecommissioning = sLDecommissioning;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogRequestInterceptor(sLDecommissioning, logService, configLogAnnotationService));
	}
}
