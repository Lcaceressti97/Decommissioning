package hn.com.tigo.equipmentaccessoriesbilling.utils;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import hn.com.tigo.equipmentaccessoriesbilling.models.GeneralError;
import hn.com.tigo.equipmentaccessoriesbilling.models.GeneralResponse;

public class Util {

	public GeneralResponse setSuccessResponse(Object data) {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(1L);
		gr.setDescription("Success");
		if (data != null) {
			gr.setData(data);
		}

		return gr;
	}

	public GeneralResponse setSuccessWithoutData() {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(1L);
		gr.setDescription("Success");
		return gr;
	}

	public GeneralResponse setSuccessEmail() {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(1L);
		gr.setDescription("Mail sent successfully");
		return gr;
	}

	public GeneralResponse setError(Exception ex, String moreInfo) {
		GeneralResponse gr = new GeneralResponse();
		GeneralError error = new GeneralError();
		error.setCode("-1");
		error.setUserMessage(ex.getMessage());
		error.setMoreInfo(moreInfo);
		error.setInternalMessage("check the log for more information");
		gr.setCode(-1L);
		gr.setDescription("Error");
		gr.getErrors().add(error);

		return gr;
	}

	public GeneralResponse setErrorResponse(GeneralError error) {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(-1L);
		gr.setDescription("Error");
		gr.getErrors().add(error);

		return gr;
	}

	public static <T> void captureAndSetResponse(HttpServletRequest request, ResponseEntity<T> responseEntity) {
		request.setAttribute("RESPONSE_ENTITY", responseEntity);
	}

	public static long sizeBytesFromBase64(String base64) {
		if (base64 == null)
			return 0;

		String cleaned = stripDataUriPrefix(base64).replaceAll("\\s+", "");

		// Usa el decodificador estándar; si sabes que viene URL-safe, usa
		// getUrlDecoder()
		byte[] decoded = Base64.getDecoder().decode(cleaned);
		return decoded.length;
	}

	/**
	 * Elimina el prefijo data URI si existe: "data:<mime>;base64,<payload>"
	 */
	private static String stripDataUriPrefix(String s) {
		int comma = s.indexOf(',');
		if (comma > 0 && s.substring(0, comma).toLowerCase().contains(";base64")) {
			return s.substring(comma + 1);
		}
		return s;
	}

	/** Convierte bytes a string legible (KB, MB, …) */
	public static String humanReadable(long bytes) {
		if (bytes < 1024)
			return bytes + " B";
		double kb = bytes / 1024.0;
		if (kb < 1024)
			return String.format("%.2f KB", kb);
		double mb = kb / 1024.0;
		if (mb < 1024)
			return String.format("%.2f MB", mb);
		double gb = mb / 1024.0;
		return String.format("%.2f GB", gb);
	}
}
