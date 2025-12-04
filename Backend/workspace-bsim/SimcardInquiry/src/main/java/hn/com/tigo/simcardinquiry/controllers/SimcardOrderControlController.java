package hn.com.tigo.simcardinquiry.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.simcardinquiry.exceptions.ExceptionHandler;
import hn.com.tigo.simcardinquiry.models.GeneralError;
import hn.com.tigo.simcardinquiry.models.GeneralResponse;
import hn.com.tigo.simcardinquiry.models.SimcardOrderControlModel;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardOrderControlService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

@RestController
@RequestMapping("/simcardordercontrol")
public class SimcardOrderControlController {

    private final ISimcardOrderControlService simcardOrderControlService;
    private final ResponseBuilder responseBuilder;
    private final ExceptionHandler exceptionHandler;

    public SimcardOrderControlController(ISimcardOrderControlService simcardOrderControlService) {
        super();
        this.simcardOrderControlService = simcardOrderControlService;
        this.responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);

    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping()
    public ResponseEntity<Object> getAll(Pageable pageable, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> this.simcardOrderControlService.getSimcardOrderControlPaginated(pageable), request);
    }

    @GetMapping("/getorderbysupplier/{idSupplier}")
    public ResponseEntity<Object> getOrderControlByIdSupplier(@PathVariable Long idSupplier,
                                                              HttpServletRequest request) {

        return exceptionHandler.handleExceptions(
                () -> this.simcardOrderControlService.getOrderControlByIdSupplier(idSupplier), request);

    }

    @GetMapping("/getorderbyinitialimsi/{imsi}")
    public ResponseEntity<Object> getOrderControlByInitialImsi(@PathVariable("imsi") String initialImsi,
                                                               HttpServletRequest request) {

        return exceptionHandler.handleExceptions(
                () -> this.simcardOrderControlService.getOrderControlByInitialImsi(initialImsi), request);

    }

    @GetMapping("/getorderbyinitialiccd/{iccd}")
    public ResponseEntity<Object> getOrderControlByInitialIccd(@PathVariable("iccd") String initialIccd,
                                                               HttpServletRequest request) {

        return exceptionHandler.handleExceptions(
                () -> this.simcardOrderControlService.getOrderControlByInitialIccd(initialIccd), request);

    }

    @GetMapping("/{idSimcardPadre}")
    public ResponseEntity<Object> getOrderByIdPadre(@PathVariable Long idSimcardPadre, HttpServletRequest request) {

        return exceptionHandler
                .handleExceptions(() -> this.simcardOrderControlService.getOrderByIdPadre(idSimcardPadre), request);

    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody SimcardOrderControlModel model, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.simcardOrderControlService.add(model);
            return model;
        }, request);
    }

    @PostMapping("/processSimcardFile/{id}")
    public ResponseEntity<GeneralResponse> processSimcardFile(@PathVariable Long id, @RequestBody byte[] fileContent,
                                                              HttpServletRequest request) {
        try {
            GeneralResponse response = simcardOrderControlService.processSimcardFile(id, fileContent);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            GeneralResponse errorResponse = new GeneralResponse();
            errorResponse.setCode(500L);
            errorResponse.setDescription("Error en la solicitud");
            GeneralError error = new GeneralError();
            error.setUserMessage("Ocurrió un error al procesar la solicitud");
            errorResponse.getErrors().add(error);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<Object> updateStatus(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> {
            this.simcardOrderControlService.updateStatus(id);
            return null;
        }, request);
    }

    @GetMapping("/orderfiles/{id}")
    public ResponseEntity<Object> getOrderFilesById(@PathVariable Long id, HttpServletRequest request) {

        return exceptionHandler.handleExceptions(() -> simcardOrderControlService.getOrderFilesById(id), request);

    }

}
