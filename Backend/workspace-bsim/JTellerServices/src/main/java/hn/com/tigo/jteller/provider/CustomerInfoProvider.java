package hn.com.tigo.jteller.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hn.com.tigo.jteller.dto.InfoCliente;
import hn.com.tigo.jteller.exceptions.BadRequestException;
import hn.com.tigo.jteller.mapper.cbsquery.customer.info.AcctProperty;
import hn.com.tigo.jteller.mapper.cbsquery.customer.info.MapperCBSQueryCustomerInfo;
import hn.com.tigo.jteller.models.AcctAccessCode;
import hn.com.tigo.jteller.models.QueryObj;
import hn.com.tigo.jteller.models.RequestBase;
import hn.com.tigo.jteller.service.AddressService;
import hn.com.tigo.jteller.services.soap.cbsquery.customer.info.AdapterException_Exception;
import hn.com.tigo.jteller.services.soap.cbsquery.customer.info.CBSQueryCustomerInfoTask;
import hn.com.tigo.jteller.services.soap.cbsquery.customer.info.CBSQueryCustomerInfoTaskService;
import hn.com.tigo.jteller.services.soap.cbsquery.customer.info.ParameterArray;
import hn.com.tigo.jteller.services.soap.cbsquery.customer.info.ParameterType;
import hn.com.tigo.jteller.services.soap.cbsquery.customer.info.TaskRequestType;
import hn.com.tigo.jteller.services.soap.cbsquery.customer.info.TaskResponseType;
import hn.com.tigo.jteller.utils.Util;

@Service
public class CustomerInfoProvider {

    // Props
    private Util util;

    public CustomerInfoProvider() {
        this.util = new Util();
    }

    /**
     * Método encargado de consumir el servicio soap CBSQueryCustomerInfoTaskService
     * para obtener la información del cliente, por el type AccountCode
     *
     * @return
     */
    public InfoCliente getCustomerInfoByAccountCode(final String accountCode) {

        InfoCliente infoCliente = new InfoCliente();

        // Instanciar la conexión
        this.buildTaskRequestTypeByAccountCode(accountCode);

        CBSQueryCustomerInfoTaskService cBSQueryCustomerInfoTaskService = new CBSQueryCustomerInfoTaskService();

        CBSQueryCustomerInfoTask cBSQueryCustomerInfoTask = cBSQueryCustomerInfoTaskService
                .getCBSQueryCustomerInfoTaskPort();

        /**
         * Este try nos ayuda a validar si funciona el mapeo que nos da la respues
         * con nuestras clases
         *
         */
        try {
            TaskResponseType taskResponseType = cBSQueryCustomerInfoTask
                    .executeTask(this.buildTaskRequestTypeByAccountCode(accountCode));
            Gson gson = new Gson();
            MapperCBSQueryCustomerInfo mapper = null;

            for (ParameterType row : taskResponseType.getParameters().getParameter()) {

                mapper = gson.fromJson(row.getValue(), MapperCBSQueryCustomerInfo.class);

                break;
            }

            // Seteando valores
            infoCliente.setAccountCode(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount()
                    .getAcctKey().toString());
            infoCliente.setCustCode(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount().getAcctInfo().getUserCustomer().getCustKey()); // Pendiente

            infoCliente.setAcctName(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount()
                    .getAcctInfo().getAcctBasicInfo().getAcctName());

            infoCliente.setAcctEmail(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount()
                    .getAcctInfo().getAcctBasicInfo().getContactInfo().getEmail());

            infoCliente.setAddress(AddressService.getAddress(mapper.getQueryCustomerInfoResultMsg()
                    .getQueryCustomerInfoResult().getAccount().getAcctInfo().getAddressInfo(), gson));

            // Setear el valor PrimaryIdentity
            List<AcctProperty> acctProperties = mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult()
                    .getAccount().getAcctInfo().getAcctBasicInfo().getAcctProperty();

            String primaryIdentity = "";

            // Recorremos todas las propiedades para obtener el número
            for (AcctProperty acctProperty : acctProperties) {

                if (acctProperty.getCode().equals("C_ACCOUNT_INFO_5")) {

                    if (acctProperty.getValue().contains("Unificada")) {
                        String[] array = acctProperty.getValue().split("-");
                        primaryIdentity = array[1];

                        break;
                    }

                } else {

                    if (acctProperty.getCode().equals("C_ACCOUNT_INFO_3")) {
                        primaryIdentity = acctProperty.getValue();
                    }


                }

            }

            infoCliente.setPrimaryIdentity(primaryIdentity);
        } catch (AdapterException_Exception e) {
            // TODO Auto-generated catch block
            throw new BadRequestException(e.getMessage());
        }

        return infoCliente;

    }

    /**
     * Se construye el request del Servicio Soap CBSQueryCustomerInfoTaskService por
     * el AccountCode
     *
     * @return
     */
    private TaskRequestType buildTaskRequestTypeByAccountCode(final String accountCode) {

        // Instanciamos la clase de retorno
        TaskRequestType taskRequestType = new TaskRequestType();

        // Instanciamos las clases que representan el json
        ParameterType parameterType = new ParameterType();
        ParameterArray parameterArray = new ParameterArray();

        QueryObj queryObj = new QueryObj();

        // Seteamos los valores
        AcctAccessCode acctAccessCode = new AcctAccessCode();
        acctAccessCode.setAccountCode(accountCode);
        acctAccessCode.setPayType("2");

        queryObj.setAcctAccessCode(acctAccessCode);

        // Seteamos el request
        RequestBase requestBase = new RequestBase();

        requestBase.setQueryObj(queryObj);

        // Crear un objeto Gson
        Gson gson = new Gson();

        // Convertir la instancia de la clase QueryObj a una cadena con formato JSON
        String json = gson.toJson(requestBase);

        parameterType.setName("JSON");
        parameterType.setValue(json);

        parameterArray.getParameter().add(parameterType);

        taskRequestType.setParameters(parameterArray);

        return taskRequestType;
    }

    /**
     * Método por el type PrimeryIdentity
     *
     * @return
     */
    public InfoCliente getCustomerInfoByPrimaryIdentity(final String primaryIdentity) {

        InfoCliente infoCliente = new InfoCliente();
        // Instanciar la conexión

        CBSQueryCustomerInfoTaskService cBSQueryCustomerInfoTaskService = new CBSQueryCustomerInfoTaskService();

        CBSQueryCustomerInfoTask cBSQueryCustomerInfoTask = cBSQueryCustomerInfoTaskService
                .getCBSQueryCustomerInfoTaskPort();

        try {
            TaskResponseType taskResponseType = cBSQueryCustomerInfoTask
                    .executeTask(this.buildTaskRequestTypeByPrimaryIdentity(primaryIdentity));
            Gson gson = new Gson();
            MapperCBSQueryCustomerInfo mapper = null;

            for (ParameterType row : taskResponseType.getParameters().getParameter()) {

                mapper = gson.fromJson(row.getValue(), MapperCBSQueryCustomerInfo.class);
                break;
            }

            // Seteando valores
            try {
                infoCliente.setAccountCode(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount()
                        .getAcctKey().toString());

                infoCliente.setCustCode(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount().getAcctInfo().getUserCustomer().getCustKey()); // Pendiente


                infoCliente.setAcctName(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount()
                        .getAcctInfo().getAcctBasicInfo().getAcctName());
                infoCliente.setAcctEmail(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount()
                        .getAcctInfo().getAcctBasicInfo().getContactInfo().getEmail());
                infoCliente.setAddress(AddressService.getAddress(mapper.getQueryCustomerInfoResultMsg()
                        .getQueryCustomerInfoResult().getAccount().getAcctInfo().getAddressInfo(), gson));

            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());

            }

            // Setear el valor PrimaryIdentity
            List<AcctProperty> acctProperties = mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult()
                    .getAccount().getAcctInfo().getAcctBasicInfo().getAcctProperty();


            String primaryIdentiti = "";

            // Recorremos todas las propiedades
            for (AcctProperty acctProperty : acctProperties) {

                if (acctProperty.getCode().equals("C_ACCOUNT_INFO_5")) {

                    if (acctProperty.getValue().contains("Unificada")) {
                        String[] array = acctProperty.getValue().split("-");
                        primaryIdentiti = array[1];

                        break;
                    }

                } else {

                    if (acctProperty.getCode().contains("C_ACCOUNT_INFO_3")) {
                        primaryIdentiti = acctProperty.getValue();
                    }

                }

            }

            infoCliente.setPrimaryIdentity(primaryIdentiti);


        } catch (AdapterException_Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return infoCliente;

    }

    /**
     * Se construye el request del Servicio Soap CBSQueryCustomerInfoTaskService por
     * el AccountCode
     *
     * @return
     */
    private TaskRequestType buildTaskRequestTypeByPrimaryIdentity(final String primaryIdentity) {

        // Instanciamos la clase de retorno
        TaskRequestType taskRequestType = new TaskRequestType();

        // Instanciamos las clases que representan el json
        ParameterType parameterType = new ParameterType();
        ParameterArray parameterArray = new ParameterArray();

        QueryObj queryObj = new QueryObj();

        // Seteamos los valores
        AcctAccessCode acctAccessCode = new AcctAccessCode();
        acctAccessCode.setAccountCode(primaryIdentity);
        acctAccessCode.setPayType("2");

        queryObj.setAcctAccessCode(acctAccessCode);

        // Seteamos el request
        RequestBase requestBase = new RequestBase();

        requestBase.setQueryObj(queryObj);

        // Crear un objeto Gson
        Gson gson = new Gson();

        // Convertir la instancia de la clase QueryObj a una cadena con formato JSON
        String json = gson.toJson(requestBase);
        String newJson = json.replace("accountCode", "primaryIdentity");

        parameterType.setName("JSON");
        parameterType.setValue(newJson);

        parameterArray.getParameter().add(parameterType);

        taskRequestType.setParameters(parameterArray);


        return taskRequestType;
    }
}
