package hn.com.tigo.equipmentblacklist.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hn.com.tigo.equipmentblacklist.models.AuthModel;
import hn.com.tigo.equipmentblacklist.models.GeneralError;
import hn.com.tigo.equipmentblacklist.models.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthValidateFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
			insertLog(cachedBodyHttpServletRequest);
			filterChain.doFilter(cachedBodyHttpServletRequest, response);
		} catch (Exception ex) {
			Gson gson = new GsonBuilder().serializeNulls().create();
			GeneralResponse customResponse = new GeneralResponse();
			GeneralError customErrors = new GeneralError();
			List<GeneralError> errorList = new ArrayList<>();
			customResponse.setCode(-1L);
			customResponse.setDescription("UNAUTHORIZED");
			customErrors.setCode("-1");
			customErrors.setUserMessage(ex.getMessage());

			errorList.add(customErrors);

			customResponse.setErrors(errorList);
			String objectToString = gson.toJson(customResponse);
			response.setContentType("application/json");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objectToString);
		}
	}

	private void insertLog(CachedBodyHttpServletRequest request) {

		try {
			AuthModel auth = new AuthModel();
			auth.setUserName(validateHeaderAuth(request));
			auth.setIp(getClientIp(request));
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(auth, auth);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			//log.info(e.getMessage());
		}

	}

	private String validateHeaderAuth(HttpServletRequest request) {
		try {
			String header = request.getHeader("Authorization");
			return header == null ? "ERROR_TEST" : header;
		} catch (Exception e) {
			return "ERROR_USER_TEST";
		}
	}

	private String getClientIp(HttpServletRequest request) {
		try {
			return request.getHeader("X-FORWARDED-FOR") == null ? request.getRemoteAddr()
					: request.getHeader("X-FORWARDED-FOR");
		} catch (Exception e) {
			return "0.0.0.0";
		}
	}
}
