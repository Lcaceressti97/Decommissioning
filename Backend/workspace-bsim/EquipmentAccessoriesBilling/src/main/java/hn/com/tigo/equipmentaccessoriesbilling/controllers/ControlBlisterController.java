package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlBlisterModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IControlBlisterService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/control-blister")
public class ControlBlisterController {

    private final IControlBlisterService controlBlisterService;
    private final ResponseBuilder responseBuilder;
    private final ExceptionHandler exceptionHandler;

    public ControlBlisterController(IControlBlisterService controlBlisterService) {
        super();
        this.controlBlisterService = controlBlisterService;
        this.responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);

    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping()
    public ResponseEntity<Object> getAll(Pageable pageable, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> controlBlisterService.getAllControlBlister(pageable), request);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.controlBlisterService.getControlBlisterById(id), request);

    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody ControlBlisterModel model, HttpServletRequest httpRequest) {

        return exceptionHandler.handleExceptions(() -> {
            this.controlBlisterService.addControlBlister(model);
            return model;
        }, httpRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ControlBlisterModel model,
                                         HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.controlBlisterService.updateControlBlister(id, model);
            return model;
        }, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.controlBlisterService.deleteControlBlister(id);
            return null;
        }, request);
    }

}
