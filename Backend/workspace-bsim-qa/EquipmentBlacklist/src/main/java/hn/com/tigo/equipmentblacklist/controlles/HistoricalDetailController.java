package hn.com.tigo.equipmentblacklist.controlles;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hn.com.tigo.equipmentblacklist.models.HistoricalDetailModel;
import hn.com.tigo.equipmentblacklist.services.interfaces.IHistoricalDetailService;
import hn.com.tigo.equipmentblacklist.utils.ResponseBuilder;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/historicaldetail")
public class HistoricalDetailController {

	private final IHistoricalDetailService historicalDetailService;
	private final ResponseBuilder responseBuilder;

	public HistoricalDetailController(IHistoricalDetailService historicalDetailService) {
		this.historicalDetailService = historicalDetailService;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		try {
			List<HistoricalDetailModel> models = this.historicalDetailService.getAll();
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/details")
	public ResponseEntity<Object> getHistoricalByEsn(@RequestParam(value = "esnImei", required = false) String esnImei,
			HttpServletRequest request) {

		try {
			List<HistoricalDetailModel> models = this.historicalDetailService.getHistoricalByEsnImei(esnImei);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		try {
			HistoricalDetailModel model = this.historicalDetailService.getById(id);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		try {
			this.historicalDetailService.delete(id);
			return responseBuilder.buildSuccessResponse(request, null);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

}
