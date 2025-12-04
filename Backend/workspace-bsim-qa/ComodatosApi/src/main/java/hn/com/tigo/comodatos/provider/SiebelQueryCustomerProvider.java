package hn.com.tigo.comodatos.provider;


import javax.xml.ws.WebServiceException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hn.com.tigo.comodatos.services.soap.siebel.cbsquery.customer.AdapterException_Exception;
import hn.com.tigo.comodatos.services.soap.siebel.cbsquery.customer.ParameterArray;
import hn.com.tigo.comodatos.services.soap.siebel.cbsquery.customer.TaskRequestType;
import hn.com.tigo.comodatos.services.soap.siebel.cbsquery.customer.TaskResponseType;
import hn.com.tigo.comodatos.soap.request.Account;
import hn.com.tigo.comodatos.soap.request.ListOfTgquerycustomerinforqio;
import hn.com.tigo.comodatos.soap.request.RequestBaseSiebel;
import hn.com.tigo.comodatos.soap.siebel.mapper.MapperSiebelCustomer;
import hn.com.tigo.comodatos.services.soap.siebel.cbsquery.customer.ParameterType;
import hn.com.tigo.comodatos.services.soap.siebel.cbsquery.customer.SiebelQueryCustomerTask;
import hn.com.tigo.comodatos.utils.Util;
import hn.com.tigo.comodatos.services.soap.siebel.cbsquery.customer.SiebelQueryCustomerTaskService;

@Service
public class SiebelQueryCustomerProvider {

	// Props
	public Util util;
	
	public SiebelQueryCustomerProvider() {
		this.util = new Util();
	}
	
	/**
	 * Método encargado de consumir el servicio soap SiebelQueryCustomerTask
	 * para obtener la cuenta servicio del cliente por el AccountCode
	 * 
	 * @param integrationId = AccountCode
	 */
	public String getServiceAccount(final String integrationId) {
		
		String serviceAccount = "0";
		
		// Construimos el request
		TaskRequestType taskRequestType = this.buildTaskRequestTypeByIntegrationId(integrationId);
		
		try {
			
			// Instanciamos la conexión
			SiebelQueryCustomerTaskService SiebelQueryCustomerTaskService = new SiebelQueryCustomerTaskService();
			
			SiebelQueryCustomerTask siebelQueryCustomerTask = SiebelQueryCustomerTaskService.getSiebelQueryCustomerTaskPort();
			
			// Ejecutamos el servicio
			TaskResponseType taskResponseType = siebelQueryCustomerTask.executeTask(taskRequestType);
			
			Gson gson = new Gson();
			MapperSiebelCustomer mapper = null;
			String object = null;
			
			for(ParameterType row: taskResponseType.getParameters().getParameter()) {
				//System.out.println(row.getValue());
				object =row.getValue();
				break;
			}
			
			
			if(object==null) {
				
				serviceAccount = "0";
				
			}else {
				
				/**
				 * Validamos si viene vacío o no el objeto
				 * 
				 */
				if(object.contains("\"listOfTgquerycustomerinforsio\":{}")) {
					//System.out.println("Esta vacío");
					serviceAccount = "0";
				}else {
					//System.out.println("Hay datos");
					mapper = gson.fromJson(object, MapperSiebelCustomer.class);
					
					//System.out.println(mapper.toString());
					
					String typeAccount = mapper.getListOfTgquerycustomerinforsio().getAccount().get(0).getAccountTypeCode();
					
					/**
					 * Validamos si el nombre del tipo de código sea el de la CUENTA SERVICIO
					 * 
					 */
					if(typeAccount.equalsIgnoreCase("CUENTA SERVICIO")) {
						serviceAccount = mapper.getListOfTgquerycustomerinforsio().getAccount().get(0).getName();
					}
					
					
				}
			}
			

		}catch (WebServiceException webEx) {
			//System.out.println("Fallo el WebServiceException");
			serviceAccount = "0";
		}catch(AdapterException_Exception adapterEx) {
			//System.out.println("Fallo el AdapterException_Exception");
			serviceAccount = "0";
		}
		

		return serviceAccount;
		
	}
	
	/**
	 * Método que nos ayuda a construir el json del request para el servicio soap
	 * 
	 * @param integrationId
	 * @return
	 */
	private TaskRequestType buildTaskRequestTypeByIntegrationId(final String integrationId) {
		
		// Instanciamos la clase de retorno
		TaskRequestType taskRequestType = new TaskRequestType();
		
		// Seteando las clases para el JSON
		Account account = new Account();
		account.setIntegrationId(integrationId);
		
		ListOfTgquerycustomerinforqio listOfTgquerycustomerinforqio = new ListOfTgquerycustomerinforqio();
		listOfTgquerycustomerinforqio.setAccount(account);
		
		RequestBaseSiebel request = new RequestBaseSiebel();
		request.setListOfTgquerycustomerinforqio(listOfTgquerycustomerinforqio);
		
		// Crear un objeto Gson
		Gson gson = new Gson();

		// Convertir la instancia de la clase QueryObj a una cadena con formato JSON
		String json = gson.toJson(request);
		//System.out.println(json);
		
		// Variable que contendrá el JSON
		ParameterType parameterType = new ParameterType();
		parameterType.setName("JSON");
		parameterType.setValue(json);
		
		ParameterArray parameterArray = new ParameterArray();
		parameterArray.getParameter().add(parameterType);
		
		//taskRequestType.setTransactionId(UUID.randomUUID().toString());
		taskRequestType.setParameters(parameterArray);
		
		
		return taskRequestType;
		
		
	}
	
}
