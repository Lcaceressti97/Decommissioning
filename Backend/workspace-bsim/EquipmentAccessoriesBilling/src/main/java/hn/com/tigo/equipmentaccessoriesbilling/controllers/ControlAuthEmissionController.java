package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
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

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlAuthEmissionModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherResponseType;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IControlAuthEmissionService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/controlauthemission")
public class ControlAuthEmissionController {

    private final IControlAuthEmissionService controlAuthEmissionService;
    private final ResponseBuilder responseBuilder;
    private final ExceptionHandler exceptionHandler;

    public ControlAuthEmissionController(IControlAuthEmissionService controlAuthEmissionService) {
        super();
        this.controlAuthEmissionService = controlAuthEmissionService;
        this.responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);

    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping()
    public ResponseEntity<Object> getAll(HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> this.controlAuthEmissionService.getAll(), request);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.controlAuthEmissionService.getById(id), request);

    }

    @GetMapping("/searchByUserCreate")
    public ResponseEntity<Object> getByUserCreate(@RequestParam String userCreate, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> controlAuthEmissionService.getByUserCreate(userCreate), request);

    }

    @GetMapping("/searchByTypeApproval")
    public ResponseEntity<Object> getByTypeApproval(Pageable pageable, @RequestParam Long typeApproval, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> controlAuthEmissionService.getByTypeApproval(pageable, typeApproval),
                request);

    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody ControlAuthEmissionModel model, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            VoucherResponseType voucherResponseType = this.controlAuthEmissionService.add(model);
            if (voucherResponseType == null) {
                return model;
            }
            return voucherResponseType;
        }, request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ControlAuthEmissionModel model,
                                         HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.controlAuthEmissionService.update(id, model);
            return model;
        }, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.controlAuthEmissionService.delete(id);
            return null;
        }, request);
    }
}
