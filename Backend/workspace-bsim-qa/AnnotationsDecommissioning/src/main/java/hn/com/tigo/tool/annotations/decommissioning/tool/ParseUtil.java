package hn.com.tigo.tool.annotations.decommissioning.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Laurent G. Cáceres
 */

public class ParseUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParseUtil.class);

	/**
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return toJson(obj, true, false);
	}

	/**
	 * @param obj
	 * @param prettyPrint
	 * @return
	 */
	public static String toJson(Object obj, boolean prettyPrint) {
		return toJson(obj, prettyPrint, false);
	}

	/**
	 * @param obj
	 * @param prettyPrint
	 * @param serializeNull
	 * @return
	 */
	public static String toJson(Object obj, boolean prettyPrint, boolean serializeNull) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Double.class, (JsonSerializer<Double>) (final Double src, final Type typeOfSrc,
				final JsonSerializationContext context) -> {
			BigDecimal value = BigDecimal.valueOf(src);
			try {
				value = new BigDecimal(value.toBigIntegerExact());
			} catch (ArithmeticException e) {
				// ignore
			}
			return new JsonPrimitive(value);
		});

		if (prettyPrint) {
			gsonBuilder = gsonBuilder.setPrettyPrinting();
		}

		Gson gson = serializeNull
				? gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING).serializeNulls().create()
				: gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING).create();

		return gson.toJson(obj);
	}

	public static <T> boolean isJsonValid(String json, Class<T> template) {
		boolean resultado = false;
		try {
			T result = fromJson(json, template);
			resultado = result != null;
		} catch (Exception ex) {
			LOGGER.error(ex.getLocalizedMessage(), ex);
		}
		return resultado;
	}

	/**
	 * @param <T>
	 * @param json
	 * @param template
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> template) {
		if (json != null && !json.isEmpty()) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.fromJson(json, template);
		}

		return null;
	}
}
