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
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlCancellationModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IControlCancellationService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/controlcancellation")
public class ControlCancellationController {

    private final IControlCancellationService controlCancellationService;
    private final ResponseBuilder responseBuilder;
    private final ExceptionHandler exceptionHandler;

    public ControlCancellationController(IControlCancellationService controlCancellationService) {
        super();
        this.controlCancellationService = controlCancellationService;
        this.responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);

    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping()
    public ResponseEntity<Object> getAll(HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.controlCancellationService.getAll(), request);

    }

    @GetMapping("/searchbytypecancellation")
    public ResponseEntity<Object> getByStatus(Pageable pageable, @RequestParam Long typeCancellation, HttpServletRequest request) {

        return exceptionHandler
                .handleExceptions(() -> controlCancellationService.getByTypeCancellation(pageable, typeCancellation), request);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.controlCancellationService.getById(id), request);

    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody ControlCancellationModel model, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.controlCancellationService.add(model);
            return model;
        }, request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ControlCancellationModel model,
                                         HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.controlCancellationService.update(id, model);
            return model;
        }, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.controlCancellationService.delete(id);
            return null;
        }, request);
    }

}
