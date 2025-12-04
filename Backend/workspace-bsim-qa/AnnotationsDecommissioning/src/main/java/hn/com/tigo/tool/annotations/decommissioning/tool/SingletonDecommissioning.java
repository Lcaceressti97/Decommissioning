package hn.com.tigo.tool.annotations.decommissioning.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hn.com.tigo.tool.annotations.services.decommissioning.interfaces.IParametersService;

/**
 *
 * @author Laurent G. Cáceres
 */

@Component
public final class SingletonDecommissioning {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SingletonDecommissioning.class);

	@Autowired
	private IParametersService parametersService;

	HashMap<String, String> params = new HashMap<>();
	String lastUpdate;

	@PostConstruct
	public void init() {
		setParamsDb();
	}

	public void setParamsDb() {
		try {
			params = parametersService.listAllParam();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			lastUpdate = df.format(Calendar.getInstance().getTime());
			LOGGER.error("Error while retrieving parameters from database", params);
		} catch (Exception ex) {
			LOGGER.error("Error while retrieving parameters from database", ex);
		}
	}

	public HashMap<String, String> getParams() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (params.isEmpty() || !lastUpdate.equals(df.format(Calendar.getInstance().getTime()))) {
			setParamsDb();
		}
		return params;
	}
}