package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import hn.com.tigo.equipmentaccessoriesbilling.models.*;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBillingService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IInvoiceDetailService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final IBillingService billingService;
    private final IInvoiceDetailService invoiceDetailService;
    private final ResponseBuilder responseBuilder;
    private final ExceptionHandler exceptionHandler;

    public BillingController(IBillingService billingService, IInvoiceDetailService invoiceDetailService) {
        super();
        this.billingService = billingService;
        this.responseBuilder = new ResponseBuilder();
        this.invoiceDetailService = invoiceDetailService;
        this.exceptionHandler = new ExceptionHandler(responseBuilder);

    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping()
    public ResponseEntity<Object> getAll(Pageable pageable, @RequestParam Long status, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getAll(pageable, status), request);

    }

    @GetMapping("/invoicesauthorizeissue")
    public ResponseEntity<Object> getInvoicesAuthorizeIssue(Pageable pageable, @RequestParam Long status,
                                                            @RequestParam String seller, HttpServletRequest request) {

        return exceptionHandler
                .handleExceptions(() -> billingService.getInvoicesAuthorizeIssue(pageable, status, seller), request);

    }

    @GetMapping("/invoicescancel")
    public ResponseEntity<Object> getInvoicesCancel(Pageable pageable, @RequestParam Long status,
                                                    @RequestParam String seller, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> billingService.getInvoicesCancel(pageable, status, seller),
                request);

    }

    @GetMapping("/findbyfilter/{value}/{type}")
    public ResponseEntity<Object> findByFilter(Pageable pageable, @PathVariable String value, @PathVariable int type,
                                               HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.findByFilter(pageable, type, value), request);

    }

    @PostMapping("/filterinvoices")
    public ResponseEntity<Object> filterInvoices(Pageable pageable, @RequestBody InvoiceFilterRequest filterRequest,
                                                 HttpServletRequest request) {
        return exceptionHandler
                .handleExceptions(
                        () -> billingService.filterInvoices(pageable, filterRequest.getStatus(), filterRequest.getWarehouses(),
                                filterRequest.getAgencies(), filterRequest.getTerritories(),
                                filterRequest.getStartDate(), filterRequest.getEndDate(), filterRequest.getSeller()),
                        request);
    }

    @GetMapping("/daterange")
    public ResponseEntity<Object> getByDateRange(Pageable pageable,
                                                 @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
                                                 @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
                                                 HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getBillsByDateRange(pageable, startDate, endDate),
                request);

    }

    @PostMapping("/invoicedetailsgraph")
    public InvoiceDetailGraphicsResponse getInvoiceDetailsGraphByFilters(
            @RequestBody InvoiceGraphFilterRequest request) {
        Optional<LocalDate> startDate = request.getStartDate();
        Optional<LocalDate> endDate = request.getEndDate();
        List<String> agencies = request.getAgencies();
        List<String> territories = request.getTerritories();

        List<String> invoiceType = request.getInvoiceType();
        List<Long> status = request.getStatus();

        InvoiceDetailGraphicsResponse response = billingService.getInvoiceDetailsGraphByDateRangeAndFilters(startDate,
                endDate, agencies, territories, invoiceType, status);

        response.setInvoicesByTypeAndStatus(billingService.getInvoiceDetailsByTypeAndStatusAndFilters(startDate,
                endDate, agencies, territories, invoiceType, status));
        response.setInvoicesByBranchOfficeAndStatus(billingService.getInvoiceDetailsByBranchOfficeAndStatus(startDate,
                endDate, agencies, territories, invoiceType, status));

        return response;
    }

    @GetMapping("/invoicesauthorizeissue/findbyid/{id}")
    public ResponseEntity<Object> getBillsByIdAuthorizeIssue(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getBillsByIdAuthorizeIssue(id), request);

    }

    @GetMapping("/invoicescancel/findbyid/{id}")
    public ResponseEntity<Object> getBillsByIdInvoicesCancel(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getBillsByIdInvoicesCancel(id), request);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getById(id), request);

    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody BillingModel model, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> {
            Long generatedId = this.billingService.add(model);

            BillingModel billingModel = this.billingService.getById(generatedId);

            List<InvoiceDetailEntity> product = this.invoiceDetailService.getDetailByIdInvoiceEntity(generatedId);

            billingModel.setInvoiceDetails(product);

            return billingModel;
        }, request);
    }

    @PostMapping("/invoiceinsuranceclaim")
    public ResponseEntity<Object> addInvoiceInsuranceClaim(@Valid @RequestBody BillingModel model, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> {
            Long generatedId = this.billingService.addInvoiceInsuranceClaim(model);

            BillingModel billingModel = this.billingService.getById(generatedId);

            List<InvoiceDetailEntity> product = this.invoiceDetailService.getDetailByIdInvoiceEntity(generatedId);

            billingModel.setInvoiceDetails(product);

            return billingModel;
        }, request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody BillingModel model,
                                         HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.billingService.update(id, model);
            return model;
        }, request);
    }

    @PatchMapping("/updatestatus/{id}")
    public ResponseEntity<Object> updateStatusInvoice(@PathVariable Long id, @RequestParam Long status,
                                                      @RequestParam(required = false) String authorizingUser,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime authorizationDate,
                                                      @RequestParam(required = false) String userIssued,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateOfIssue,
                                                      HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.billingService.updateStatusInvoice(id, status, authorizingUser, authorizationDate, userIssued,
                    dateOfIssue);
            return null;
        }, request);
    }

    @PatchMapping("/updatedocumentno/{id}")
    public ResponseEntity<Object> updateDocumentNo(@PathVariable Long id, @RequestParam String documentNo,
                                                   HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.billingService.updateDocumentNo(id, documentNo);
            return null;
        }, request);
    }

    @PutMapping("/updatecorporateclient/{id}")
    public ResponseEntity<Object> updateCorporateClient(@PathVariable Long id, @RequestBody BillingModel model,
                                                        HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.billingService.updateCorporateClient(id, model);
            return null;
        }, request);

    }

    @PutMapping("/updatesingleclient/{id}")
    public ResponseEntity<Object> updateSingleClient(@PathVariable Long id, @RequestBody BillingModel model,
                                                     HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.billingService.updateSingleClient(id, model);
            return null;
        }, request);

    }

    @PatchMapping("/updatestatusexotax/{id}")
    public ResponseEntity<Object> updateStatusExoTax(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.billingService.updateStatusExoTax(id);
            return null;
        }, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.billingService.delete(id);
            return null;
        }, request);
    }

    @GetMapping("invoiceprintingdetail/{id}")
    public ResponseEntity<Object> getInvoiceDetailBase64ById(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getInvoiceDetailBase64ById(id), request);

    }

    @GetMapping("/invoicebyserie/{serie}")
    public ResponseEntity<Object> getInvoiceBySerialNumber(@PathVariable String serie, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getInvoiceBySerialNumber(serie), request);

    }

    @GetMapping("/insuranceclaim/{idInsuranceClaim}")
    public ResponseEntity<Object> getBillingByInsuranceClaim(@PathVariable Long idInsuranceClaim,
                                                             HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getBillingByInsuranceClaim(idInsuranceClaim),
                request);

    }

    @PostMapping("/resendTrama")
    public ResponseEntity<Object> resendTrama(@Valid @RequestBody ResendTramaRequest request, HttpServletRequest httpRequest) {
        return exceptionHandler.handleExceptions(() -> {
            return this.billingService.resendTrama(request);
        }, httpRequest);
    }

    @GetMapping("/creation")
    public ResponseEntity<Object> getInvoicesOfTheDayAndStatusAndSeller(Pageable pageable, @RequestParam String seller, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.billingService.getInvoicesOfTheDayAndStatusAndSeller(pageable, seller), request);

    }

}
