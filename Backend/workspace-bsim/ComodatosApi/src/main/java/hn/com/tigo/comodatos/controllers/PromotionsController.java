package hn.com.tigo.comodatos.controllers;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hn.com.tigo.comodatos.exceptions.ExceptionHandler;
import hn.com.tigo.comodatos.models.PromotionsModel;
import hn.com.tigo.comodatos.responses.DescTotal;
import hn.com.tigo.comodatos.services.interfaces.IPromotionsService;
import hn.com.tigo.comodatos.soap.request.PromotionsRequest;
import hn.com.tigo.comodatos.utils.ResponseBuilder;

@RestController
@RequestMapping("/promotions")
public class PromotionsController {

    private final IPromotionsService promotionsService;
    private final ResponseBuilder responseBuilder;
    private final ExceptionHandler exceptionHandler;

    public PromotionsController(IPromotionsService promotionsService) {
        this.promotionsService = promotionsService;
        this.responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);
    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping
    public ResponseEntity<Object> getAllPromotions(HttpServletRequest request) {
        return exceptionHandler.handleExceptions(promotionsService::getAllPromotions, request);
    }

    @GetMapping("/model/{modelCode}")
    public ResponseEntity<Object> getPromotionsByModelCode(@PathVariable String modelCode,
                                                           HttpServletRequest request) {
        return exceptionHandler.handleExceptions(
                () -> promotionsService.getPromotionsByModelCode(modelCode),
                request
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPromotionsById(@PathVariable Long id, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> promotionsService.getPromotionsById(id), request);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addPromotions(@Valid @RequestBody PromotionsModel model,
                                                HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> {
            promotionsService.addPromotions(model);
            return model;
        }, request);
    }

    @PostMapping("/search")
    public ResponseEntity<Object> searchModels(@Valid @RequestBody PromotionsRequest model,
                                                HttpServletRequest request) {
        return exceptionHandler.handleExceptions(
                () -> promotionsService.searchModels(
                        model.getPrecio_promo(),
                        model.getMeses_permanencia(),
                        model.getCodigo_modelo()
                ),
                request
        );
    }

    @PostMapping("/allowance")
    public ResponseEntity<Object> getDesc(@Valid @RequestBody PromotionsRequest model,
                                          HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> {
            BigDecimal total = promotionsService.getDesc(
                    model.getPrecio_promo(),
                    model.getMeses_permanencia(),
                    model.getCodigo_modelo(),
                    model.getTipo_cliente(),
                    model.getCurrent_date(),
                    model.getFinanciado(),
                    model.getGross()
            );
            return new DescTotal(total);
        }, request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePromotions(@PathVariable Long id,
                                                   @Valid @RequestBody PromotionsModel model,
                                                   HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> {
            promotionsService.updatePromotions(id, model);
            return model;
        }, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePromotions(@PathVariable Long id, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> {
            promotionsService.deletePromotions(id);
            return null;
        }, request);
    }
}