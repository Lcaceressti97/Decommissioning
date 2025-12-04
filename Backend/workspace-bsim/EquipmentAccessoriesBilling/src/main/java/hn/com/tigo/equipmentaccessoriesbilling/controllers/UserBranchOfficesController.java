package hn.com.tigo.equipmentaccessoriesbilling.controllers;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.entities.UsersEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.UserBranchOfficesModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUserBranchOfficesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUsersRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IUserBranchOfficesService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/userbranchoffices")
public class UserBranchOfficesController {

	private final IUserBranchOfficesService userBranchOfficesService;
	private final IUsersRepository usersRepository;
	private final ResponseBuilder responseBuilder;

	public UserBranchOfficesController(IUserBranchOfficesService userBranchOfficesService, IUsersRepository usersRepository, IUserBranchOfficesRepository userBranchOfficesRepository) {
		super();
		this.userBranchOfficesService = userBranchOfficesService;
		this.usersRepository = usersRepository;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping("/validate/iduser/{id}")
	public ResponseEntity<Object> validate(@PathVariable Long id, HttpServletRequest request) {
		try {
			
			UsersEntity userEntity = this.usersRepository.findById(id).orElse(null);
			if (userEntity == null)
				throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, id));



			//UserBranchOfficesEntity userBranchOfficesEntity = this.userBranchOfficesRepository
				//	.findByIdUserActivated(id);

			//if (userBranchOfficesEntity != null && userEntity.getStatus() != 0) {
			//	throw new BadRequestException(String.format(Constants.USER_BRANCH_VALIDATION));
			//}
			
			//this.userBranchOfficesService.add(model);
			return responseBuilder.buildSuccessResponse(request, userEntity);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody UserBranchOfficesModel model, HttpServletRequest request) {
		try {
			this.userBranchOfficesService.add(model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PatchMapping("/updatestatus")
	public ResponseEntity<Object> updateStatus(@RequestParam Long idUser, @RequestParam Long idBranchOffices,
			@RequestParam Long status, HttpServletRequest request) {
		try {
			this.userBranchOfficesService.registerOrCancelUser(idUser, idBranchOffices, status);
			return responseBuilder.buildSuccessResponse(request, null);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

}
