package hn.com.tigo.tool.annotations.decommissioning.module;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import hn.com.tigo.tool.annotations.decommissioning.api.Log;
import hn.com.tigo.tool.annotations.decommissioning.constants.Constants;
import hn.com.tigo.tool.annotations.decommissioning.entities.ConfigLogAnnotationEntity;
import hn.com.tigo.tool.annotations.decommissioning.models.LogModel;
import hn.com.tigo.tool.annotations.decommissioning.tool.ParseUtil;
import hn.com.tigo.tool.annotations.decommissioning.tool.SingletonDecommissioning;
import hn.com.tigo.tool.annotations.services.decommissioning.interfaces.IConfigLogAnnotationService;
import hn.com.tigo.tool.annotations.services.decommissioning.interfaces.ILogService;

@Component
public class LogRequestInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogRequestInterceptor.class);

	private static final String TEMPLATE = "{\n" + "  \"headers\": \"%s\",\n" + "  \"entity\": %s\n" + "}";

	@Context
	private HttpServletRequest servletRequest;

	SingletonDecommissioning sLDecommissioning;

	@Autowired
	private ILogService logService;

	@Autowired
	private IConfigLogAnnotationService configLogAnnotationService;

	@Context
	ResourceInfo resourceInfo;

	public LogRequestInterceptor(SingletonDecommissioning sLDecommissioning, ILogService logService,
			IConfigLogAnnotationService configLogAnnotationService) {
		super();
		this.sLDecommissioning = sLDecommissioning;
		this.logService = logService;
		this.configLogAnnotationService = configLogAnnotationService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		LocalDateTime startTime = LocalDateTime.now();
		request.setAttribute("startTime", startTime);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Log logRequestAnnotation = handlerMethod.getMethodAnnotation(Log.class);
			if (logRequestAnnotation != null) {
				LogModel logEntity = new LogModel();
				LocalDateTime startTime = (LocalDateTime) request.getAttribute("startTime");
				LocalDateTime endTime = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String starDate = startTime.format(formatter);
				String endDate = endTime.format(formatter);

				String ds = logRequestAnnotation.value();
				String am = logRequestAnnotation.valueMethod();
				String pr = logRequestAnnotation.project();
				try {
					String headersJson = "";
					String entityJson = "";

					if ("1".equals(sLDecommissioning.getParams().get("INSERT_LOG_ANNOTATION"))) {
						ConfigLogAnnotationEntity dtoStatus = configLogAnnotationService
								.findByProjectAndMethodContaining(pr, am);
						if (dtoStatus != null && "1".equals(dtoStatus.getStatus())) {
							String scheme = request.getScheme();
							String serverName = request.getServerName();
							int serverPort = request.getServerPort();
							String contextPath = request.getContextPath();
							String servletPath = request.getServletPath();
							String uri = scheme + "://" + serverName + ":" + serverPort + contextPath + servletPath;
							String method = request.getMethod();
							Enumeration<String> headerNames = request.getHeaderNames();
							Map<String, String> headersMap = Collections.list(headerNames).stream()
									.collect(Collectors.toMap(name -> name, request::getHeader));
							headersJson = headersMap.entrySet().stream()
									.map(entry -> "\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"")
									.collect(Collectors.joining(", ", "{", "}"));

							if ("POST".equalsIgnoreCase(method)) {
								BufferedReader reader = request.getReader();
								StringBuilder entityStringBuilder = new StringBuilder();
								String line;
								while ((line = reader.readLine()) != null) {
									entityStringBuilder.append(line);
								}
								entityJson = entityStringBuilder.toString();
							}
							String requestJson = String.format(TEMPLATE, headersJson, entityJson);

							ResponseEntity<?> responseEntity = (ResponseEntity<?>) request
									.getAttribute(Constants.RESPONSE);
							System.out.println(responseEntity);
							Object responseBody = responseEntity.getBody();
							if (responseBody != null) {
								entityJson = responseBody.toString();
							}

							String res = String.format(TEMPLATE, headersJson, ParseUtil.toJson(responseBody));
							LOGGER.info("Se obtienen valores del request hacia la entidad");
							logEntity.setIdApp(-1L);
							logEntity.setDataSource(ds);
							logEntity.setStartDate(starDate);
							logEntity.setEndDate(endDate);
							logEntity.setRequest(requestJson);
							logEntity.setResponse(res);
							logEntity.setMethod(method);
							logEntity.setApiMethod(am);
							logEntity.setUri(uri);
							logEntity.setHttpResponseCode(String.valueOf(response.getStatus()));
							LOGGER.info("Se obtienen valores del request hacia la entidad");
							logService.saveLog(logEntity);
						}
					}
				} catch (Exception exx) {
					LOGGER.error(exx.getLocalizedMessage(), exx);
				}
			}
		}
	}

}
