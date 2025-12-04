package hn.com.tigo.jteller.filtes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hn.com.tigo.jteller.models.GeneralResponse;
import hn.com.tigo.jteller.models.GeneralError;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthValidateFilter extends OncePerRequestFilter{
	
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            
            // Este método lanza el próximo filter
            filterChain.doFilter(cachedBodyHttpServletRequest,response);
        }catch (Exception ex) {
        	
        	// Instanciamos el Gson para dar formato json al response
            Gson gson = new GsonBuilder().serializeNulls().create();
            GeneralResponse customResponse =  new GeneralResponse();
            GeneralError customErrors = new GeneralError();
            
            // Instanciamos y seteamos los valores de error
            List<GeneralError> errorList = new ArrayList<>();
            customResponse.setCode(-1L);
            customResponse.setDescription("UNAUTHORIZED");
            customErrors.setCode("-1");
            customErrors.setUserMessage(ex.getMessage());

            errorList.add(customErrors);

            // Seteamos el valor de respuesta
            customResponse.setErrors(errorList);
            String objectToString = gson.toJson(customResponse);
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectToString);
        }
    }



    private String validateHeaderAuth(HttpServletRequest request) {
        try{
            String header = request.getHeader("Authorization");
            return header == null? "ERROR_TEST": header;
        }catch (Exception e) {
            return "ERROR_USER_TEST";
        }
    }

    private String getClientIp(HttpServletRequest request){
        try{
            return request.getHeader("X-FORWARDED-FOR") == null ? request.getRemoteAddr() :request.getHeader("X-FORWARDED-FOR");
        }catch (Exception e) {
            return "0.0.0.0";
        }
    }

}
