package hn.com.tigo.comodatos.provider;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hn.com.tigo.comodatos.models.AccountCBS;
import hn.com.tigo.comodatos.services.soap.cbsquery.customer.info.AdapterException_Exception;
import hn.com.tigo.comodatos.services.soap.cbsquery.customer.info.CBSQueryCustomerInfoTask;
import hn.com.tigo.comodatos.services.soap.cbsquery.customer.info.CBSQueryCustomerInfoTaskService;
import hn.com.tigo.comodatos.services.soap.cbsquery.customer.info.ParameterArray;
import hn.com.tigo.comodatos.services.soap.cbsquery.customer.info.ParameterType;
import hn.com.tigo.comodatos.services.soap.cbsquery.customer.info.TaskRequestType;
import hn.com.tigo.comodatos.services.soap.cbsquery.customer.info.TaskResponseType;
import hn.com.tigo.comodatos.soap.mapper.MapperCBSQueryCustomerInfo;
import hn.com.tigo.comodatos.soap.request.AcctAccessCode;
import hn.com.tigo.comodatos.soap.request.QueryObj;
import hn.com.tigo.comodatos.soap.request.RequestBase;


@Service
public class CustomerInfoProvider {
	
	// Props


	public CustomerInfoProvider() {
		
	}
	
	
	public AccountCBS getCustomerInfoByPrimaryIdentity(final String subscriber) {
		
		AccountCBS retorno = new AccountCBS();
		
		CBSQueryCustomerInfoTaskService cBSQueryCustomerInfoTaskService = new CBSQueryCustomerInfoTaskService();

		CBSQueryCustomerInfoTask cBSQueryCustomerInfoTask = cBSQueryCustomerInfoTaskService
				.getCBSQueryCustomerInfoTaskPort();
		
		try {
			
			TaskResponseType taskResponseType = cBSQueryCustomerInfoTask.executeTask(this.buildTaskRequestTypeByPrimaryIdentity(subscriber));
			
			Gson gson = new Gson();
			MapperCBSQueryCustomerInfo mapper = null;
			
			for( ParameterType row: taskResponseType.getParameters().getParameter()) {
				mapper = gson.fromJson(row.getValue(), MapperCBSQueryCustomerInfo.class);
				
				break;
			}
			
			retorno.setBillingAccount(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount().getAcctKey().toString());
			retorno.setCustomerAccount(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount().getAcctInfo().getUserCustomer().getCustKey());
			
		}catch(AdapterException_Exception e) {
			throw new BadRequestException(e.getMessage());
		}
		
		return retorno;
	}
	
	// Methods
	
	
	/**
	 * Se construye el request del Servicio Soap CBSQueryCustomerInfoTaskService por
	 * el AccountCode
	 * 
	 * @return
	 */
	private TaskRequestType buildTaskRequestTypeByPrimaryIdentity(final String subscriber) {

		// Instanciamos la clase de retorno
		TaskRequestType taskRequestType = new TaskRequestType();

		// Instanciamos las clases que representan el json
		ParameterType parameterType = new ParameterType();
		ParameterArray parameterArray = new ParameterArray();

		QueryObj queryObj = new QueryObj();

		// Seteamos los valores
		AcctAccessCode acctAccessCode = new AcctAccessCode();
		acctAccessCode.setPrimaryIdentity(subscriber);
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
	
}
