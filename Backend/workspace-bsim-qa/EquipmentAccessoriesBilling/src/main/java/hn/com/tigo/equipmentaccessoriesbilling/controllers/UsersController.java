package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.UsersModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IUsersService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/users")
public class UsersController {

	private final IUsersService usersService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public UsersController(IUsersService usersService) {
		super();
		this.usersService = usersService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.usersService.getUsersWithoutPermissions(), request);

	}

	@GetMapping("/searchbyusername")
	public ResponseEntity<Object> getByUserName(@RequestParam String username, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.usersService.getByUserName(username), request);

	}

	@GetMapping("/{idbranchoffices}/{status}")
	public ResponseEntity<Object> getUsersByBranchAndStatus(@PathVariable("idbranchoffices") Long idBranchOffices,
			@PathVariable("status") Long status, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(
				() -> this.usersService.getUsersByBranchOfficeAndStatus(idBranchOffices, status), request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.usersService.getById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody UsersModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.usersService.add(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody UsersModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.usersService.update(id, model);
			return model;
		}, request);
	}

	@PatchMapping("/updatestatus/{id}")
	public ResponseEntity<Object> updateStatusUser(@PathVariable Long id, @RequestParam Long status,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.usersService.updateStatus(id, status);
			return null;
		}, request);
	}

	@PatchMapping("/assignbranchuser/{id}")
	public ResponseEntity<Object> assignBranchUser(@PathVariable Long id, @RequestParam Long idBranchOffices,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.usersService.assignBranchUser(id, idBranchOffices);
			return null;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.usersService.delete(id);
			return null;
		}, request);
	}

}
