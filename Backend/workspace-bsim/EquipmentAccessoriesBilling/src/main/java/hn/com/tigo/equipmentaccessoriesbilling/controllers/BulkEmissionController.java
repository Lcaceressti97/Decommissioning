package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmissionBatchResult;
import hn.com.tigo.equipmentaccessoriesbilling.models.BulkEmitRequest;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBulkEmissionService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bulk-emission")
public class BulkEmissionController {

    private final IBulkEmissionService bulkEmissionService;
    private final ExceptionHandler exceptionHandler;

    public BulkEmissionController(IBulkEmissionService bulkEmissionService) {
        super();
        this.bulkEmissionService = bulkEmissionService;
        ResponseBuilder responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);

    }

    @GetMapping()
    public ResponseEntity<Object> getInvoicesBulkEmission(Pageable pageable, @RequestParam String seller, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> bulkEmissionService.getBulkEmission(pageable, seller), request);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchByCustomerOrCustomerId(
            Pageable pageable,
            @RequestParam(required = false) String seller,
            @RequestParam(required = false) String customer,
            @RequestParam(name = "customerId", required = false) String customerId,
            HttpServletRequest request) {

        return exceptionHandler.handleExceptions(
                () -> bulkEmissionService.searchByCustomerOrCustomerId(pageable, seller, customer, customerId),
                request
        );
    }

    @PostMapping("/emission")
    public ResponseEntity<BulkEmissionBatchResult> bulkEmission(@RequestBody BulkEmitRequest req) {
        BulkEmissionBatchResult result = bulkEmissionService.emitBulk(
                req.getIdsPrefectures(),
                req.getUserCreate(),
                req.getDescription(),
                req.getIdBranchOffices(),
                req.getPaymentCode()
        );
        return ResponseEntity.ok(result);
    }

}
