package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBillingRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IChannelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.VoucherException;
import hn.com.tigo.equipmentaccessoriesbilling.models.CancelVoucherRequest;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.EmitVoucherModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.GeneralError;
import hn.com.tigo.equipmentaccessoriesbilling.models.GeneralResponse;
import hn.com.tigo.equipmentaccessoriesbilling.provider.VoucherProvider;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.MessageFaultMsg;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherACKType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherResponseType;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Util;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherProvider voucherProvider;
    private final IConfigParametersService configParametersService;
    private final Util util;
    private final ResponseBuilder responseBuilder;
    private final IBillingRepository billingRepository;
    private final IChannelRepository channelRepository;

    public VoucherController(VoucherProvider voucherProvider, IConfigParametersService configParametersService, IBillingRepository billingRepository, IChannelRepository channelRepository) {
        super();
        this.voucherProvider = voucherProvider;
        this.configParametersService = configParametersService;
        this.billingRepository = billingRepository;
        this.channelRepository = channelRepository;
        this.util = new Util();
        this.responseBuilder = new ResponseBuilder();
    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping(path = "/execute/getVoucher/{id}")
    public ResponseEntity<Object> getVoucherById(@PathVariable Long id) {

        VoucherResponseType voucherResponseType = null;

        try {
            List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(1001L);
            Map<String, String> parameters = new HashMap<>();

            for (ConfigParametersModel parameter : list) {
                parameters.put(parameter.getParameterName(), parameter.getParameterValue());
            }

            voucherResponseType = this.voucherProvider.getVoucher(id, parameters);

        } catch (BadRequestException ex) {

            GeneralError error = new GeneralError();
            error.setCode("400");
            error.setUserMessage(ex.getMessage());
            error.setMoreInfo("No records found for the idReference: " + id);
            error.setInternalMessage("check the log for more information");

            GeneralResponse gr = new Util().setErrorResponse(error);
            return ResponseEntity.badRequest().body(gr);
        } catch (Exception e) {
            GeneralError error = new GeneralError();
            error.setCode("400");
            error.setUserMessage("Error getVoucher");
            error.setMoreInfo(e.getMessage());
            error.setInternalMessage("check the log for more information");

            GeneralResponse gr = new Util().setErrorResponse(error);
            return ResponseEntity.badRequest().body(gr);
        }

        return ResponseEntity.ok(this.util.setSuccessResponse(voucherResponseType));
    }

    @PostMapping(path = "/execute/addVoucher")
    public ResponseEntity<Object> addVoucherTest(@RequestBody EmitVoucherModel model) {

        // Obtenemos los parámetros para este servicio
        List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(1001L);
        Map<String, String> parameters = new HashMap<>();

        for (ConfigParametersModel parameter : list) {
            parameters.put(parameter.getParameterName(), parameter.getParameterValue());
        }

        VoucherResponseType response = null;

        try {
            response = this.voucherProvider.executeAddVoucher(model.getId(), model.getUserName(), model.getIdBranches(),
                    null, parameters, null);
            return ResponseEntity.ok(this.util.setSuccessResponse(response));
        } catch (VoucherException ex) {
            GeneralError error = new GeneralError();
            error.setCode("400");
            error.setUserMessage("Error al consumir el addVocuher");
            error.setMoreInfo(ex.getMessage());
            error.setInternalMessage("check the log for more information");

            GeneralResponse gr = new Util().setErrorResponse(error);
            return ResponseEntity.badRequest().body(gr);
        } catch (BadRequestException ex) {
            GeneralError error = new GeneralError();
            error.setCode("400");
            error.setUserMessage("Error al consumir el addVocuher");
            error.setMoreInfo(ex.getMessage());
            error.setMoreInfo("No records found for the id: " + model.getId());
            error.setInternalMessage("check the log for more information");

            GeneralResponse gr = new Util().setErrorResponse(error);
            return ResponseEntity.badRequest().body(gr);
        } catch (Exception ex) {
            GeneralError error = new GeneralError();
            error.setCode("400");
            error.setUserMessage("Error al consumir el addVocuher");
            error.setMoreInfo(ex.getMessage());
            error.setInternalMessage("check the log for more information");

            GeneralResponse gr = new Util().setErrorResponse(error);
            return ResponseEntity.badRequest().body(gr);
        }

    }

    @PostMapping(path = "/cancelvoucher")
    public ResponseEntity<Object> cancelVoucher(@RequestBody CancelVoucherRequest request,
                                                HttpServletRequest httpRequest) {
        try {
            List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(1001L);
            Map<String, String> parameters = new HashMap<>();

            for (ConfigParametersModel parameter : list) {
                parameters.put(parameter.getParameterName(), parameter.getParameterValue());
            }

            BillingEntity billingEntity = this.billingRepository.findById(request.getIdInvoice()).orElse(null);

            assert billingEntity != null;
            ChannelEntity channelEntity = this.channelRepository.findById(billingEntity.getChannel()).orElse(null);

            VoucherACKType voucherACKType = this.voucherProvider.cancelVoucher(request.getIdInvoice(),
                    request.getUser(), parameters, channelEntity);
            return responseBuilder.buildSuccessResponse(httpRequest, voucherACKType);
        } catch (MessageFaultMsg ex) {
            return responseBuilder.buildErrorResponseVoucher(ex, httpRequest);

        }
    }

}
