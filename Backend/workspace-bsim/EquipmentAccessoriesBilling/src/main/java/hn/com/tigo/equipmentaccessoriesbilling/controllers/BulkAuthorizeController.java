package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkAuthorizeRequest;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBulkAuthorizeService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bulk-authorize")
public class BulkAuthorizeController {

    private final IBulkAuthorizeService bulkAuthorizeService;
    private final ExceptionHandler exceptionHandler;

    public BulkAuthorizeController(IBulkAuthorizeService bulkAuthorizeService) {
        super();
        this.bulkAuthorizeService = bulkAuthorizeService;
        ResponseBuilder responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);

    }

    @GetMapping()
    public ResponseEntity<Object> getInvoicesBulkAuthorize(Pageable pageable, @RequestParam String seller, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> bulkAuthorizeService.getBulkAuthorize(pageable, seller), request);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchByCustomerOrCustomerId(
            Pageable pageable,
            @RequestParam(required = false) String seller,
            @RequestParam(required = false) String customer,
            @RequestParam(name = "customerId", required = false) String customerId,
            HttpServletRequest request) {

        return exceptionHandler.handleExceptions(
                () -> bulkAuthorizeService.searchByCustomerOrCustomerId(pageable, seller, customer, customerId),
                request
        );
    }


    @PostMapping("/authorize")
    public ResponseEntity<Object> authorizeInvoices(
            @RequestBody BulkAuthorizeRequest req,
            HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> bulkAuthorizeService.authorizeInvoices(req), request);
    }
}
