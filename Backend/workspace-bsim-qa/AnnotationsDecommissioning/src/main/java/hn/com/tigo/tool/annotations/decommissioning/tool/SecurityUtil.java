package hn.com.tigo.tool.annotations.decommissioning.tool;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Laurent G. Cáceres
 */

public class SecurityUtil {

	//private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

	public static String getClientIp(HttpServletRequest request) {
		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		return remoteAddr;
	}

}
