package hn.com.tigo.equipmentblacklist.controlles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hn.com.tigo.equipmentblacklist.models.AddBloqueoImeiRequestModel;
import hn.com.tigo.equipmentblacklist.models.HistoricalModel;
import hn.com.tigo.equipmentblacklist.models.GeneralResponseImeiModel;
import hn.com.tigo.equipmentblacklist.models.RemoveBloqueoImeiRequestModel;
import hn.com.tigo.equipmentblacklist.services.interfaces.IHistoricalService;
import hn.com.tigo.equipmentblacklist.utils.ResponseBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/historical")
public class HistoricalController {

	private final IHistoricalService historicalService;
	private final ResponseBuilder responseBuilder;

	public HistoricalController(IHistoricalService historicalService) {
		this.historicalService = historicalService;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAllHistorical(Pageable pageable, HttpServletRequest request) {

		try {
			Page<HistoricalModel> models = this.historicalService.getAllHistoricalByPagination(pageable);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getHistoricalById(@PathVariable Long id, HttpServletRequest request) {

		try {
			HistoricalModel model = this.historicalService.getById(id);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/searchhistorical")
	public ResponseEntity<Object> getHistoricalByType(@RequestParam(value = "type", required = false) int type,
			@RequestParam(value = "value", required = false) String value, HttpServletRequest request) {

		try {
			List<HistoricalModel> models = this.historicalService.findByWithType(type, value);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Object> addHistorical(@Valid @RequestBody HistoricalModel model, HttpServletRequest request) {

		try {
			this.historicalService.add(model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PostMapping("addbloqueoimei")
	public ResponseEntity<?> addBloqueoImei(@RequestBody AddBloqueoImeiRequestModel request,
			HttpServletRequest httpRequest) {

		try {
			GeneralResponseImeiModel response = historicalService.addBloqueoImei(request);
			return responseBuilder.buildSuccessResponseBloqueo(httpRequest, response);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, httpRequest);
		}
	}

	@PostMapping("removebloqueoimei")
	//@Log(value = Constants.DATASOURCE, valueMethod = "/removeBloqueoImei/", project = "BlacklistApi")
	public ResponseEntity<?> removeBloqueoImei(@RequestBody RemoveBloqueoImeiRequestModel request,
			HttpServletRequest httpRequest) {

		try {
			GeneralResponseImeiModel response = historicalService.removeBloqueoImei(request);
			return responseBuilder.buildSuccessResponseBloqueo(httpRequest, response);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, httpRequest);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateHistorical(@PathVariable Long id, @Valid @RequestBody HistoricalModel model,
			HttpServletRequest request) {
		try {
			this.historicalService.update(id, model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteHistorical(@PathVariable Long id, HttpServletRequest request) {
		try {
			this.historicalService.delete(id);
			return responseBuilder.buildSuccessResponse(request, null);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

}
