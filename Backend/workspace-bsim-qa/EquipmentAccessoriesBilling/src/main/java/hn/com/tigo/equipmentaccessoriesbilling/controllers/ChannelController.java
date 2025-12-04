package hn.com.tigo.equipmentaccessoriesbilling.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.ChannelModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IChannelService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/channel")
public class ChannelController {

	private final IChannelService channelService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ChannelController(IChannelService channelService) {
		super();
		this.channelService = channelService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> channelService.getAllChannels(), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.channelService.getChannelById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody ChannelModel model, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.channelService.addChannel(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ChannelModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.channelService.updateChannel(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.channelService.deleteChannel(id);
			return null;
		}, request);
	}

}
