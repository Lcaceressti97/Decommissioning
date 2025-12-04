package hn.com.tigo.equipmentblacklist.controlles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentblacklist.models.ImeiControlFileModel;
import hn.com.tigo.equipmentblacklist.services.interfaces.ImeiControlFileService;
import hn.com.tigo.equipmentblacklist.utils.ResponseBuilder;

@RestController
@RequestMapping("/imeicontrolfile")
public class ImeiControlFileController {

	private final ImeiControlFileService imeiControlFileService;
	private final ResponseBuilder responseBuilder;

	public ImeiControlFileController(ImeiControlFileService imeiControlFileService) {
		this.imeiControlFileService = imeiControlFileService;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		try {
			List<ImeiControlFileModel> listModel = this.imeiControlFileService.getAll();
			return responseBuilder.buildSuccessResponse(request, listModel);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {
		try {
			ImeiControlFileModel model = this.imeiControlFileService.getById(id);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/searchbyphone")
	public ResponseEntity<Object> getByPhone(@RequestParam(value = "phone", required = false) String phone,
			HttpServletRequest request) {
		try {
			List<String> errors = new ArrayList<>();
			String phoneValue = phone.trim();
			if (phoneValue != null) {
				if (!phoneValue.matches("[0-9]+")) {
					errors.add("El parámetro 'phone' debe contener solo números.");
				}
				if (phoneValue.length() > 12) {
					errors.add("El parámetro 'phone' no puede ser mayor de 12 caracteres.");
				}
			}

			if (!errors.isEmpty()) {
				return ResponseEntity.badRequest().body(getErrorResponse(errors, phoneValue));
			}

			List<ImeiControlFileModel> models = this.imeiControlFileService.getByPhone(phoneValue);

			List<Map<String, Object>> data = new ArrayList<>();
			for (ImeiControlFileModel model : models) {
				Map<String, Object> dataItem = new HashMap<>();
				dataItem.put("imei", model.getImei());
				dataItem.put("imsi", model.getImsi());
				dataItem.put("status", model.getStatus());
				dataItem.put("createdDate", model.getCreatedDate());
				data.add(dataItem);
			}
			return responseBuilder.buildSuccessResponseImei(request, data, phoneValue);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/searchbyphoneorimei/{value}/{type}")
	public ResponseEntity<Object> getByPhoneOrImei(@PathVariable String value, @PathVariable int type,
			HttpServletRequest request) {
		try {
			List<ImeiControlFileModel> models = this.imeiControlFileService.findByPhoneOrImeiWithType(type, value);

			List<Map<String, Object>> data = new ArrayList<>();
			for (ImeiControlFileModel model : models) {
				Map<String, Object> dataItem = new HashMap<>();
				dataItem.put("transactionId", model.getTransactionId());
				dataItem.put("phone", model.getPhone());
				dataItem.put("imei", model.getImei());
				dataItem.put("imsi", model.getImsi());
				dataItem.put("status", model.getStatus());
				dataItem.put("createdDate", model.getCreatedDate());
				data.add(dataItem);
			}
			return responseBuilder.buildSuccessResponse(request, data);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody ImeiControlFileModel model, HttpServletRequest request) {
		try {

			this.imeiControlFileService.add(model);
			return responseBuilder.buildSuccessResponse(request, model);

		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ImeiControlFileModel model,
			HttpServletRequest request) {
		try {
			this.imeiControlFileService.update(id, model);
			return responseBuilder.buildSuccessResponse(request, null);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		try {
			this.imeiControlFileService.delete(id);
			return responseBuilder.buildSuccessResponse(request, null);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	private Map<String, Object> getErrorResponse(List<String> errors, String phone) {
		Map<String, Object> response = new HashMap<>();
		response.put("code", 1);
		response.put("description", "Validation Error");
		response.put("phone", phone);
		response.put("data", new ArrayList<>());
		response.put("errors", errors);
		return response;
	}
}
