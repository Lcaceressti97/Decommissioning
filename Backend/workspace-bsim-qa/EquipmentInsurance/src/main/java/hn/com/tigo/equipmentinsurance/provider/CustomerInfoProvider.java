package hn.com.tigo.equipmentinsurance.provider;

import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentinsurance.mapper.AcctProperty;
import hn.com.tigo.equipmentinsurance.models.*;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hn.com.tigo.equipmentinsurance.mapper.MapperCBSQueryCustomerInfo;
import hn.com.tigo.equipmentinsurance.services.customerinfo.AdapterException_Exception;
import hn.com.tigo.equipmentinsurance.services.customerinfo.CBSQueryCustomerInfoTask;
import hn.com.tigo.equipmentinsurance.services.customerinfo.CBSQueryCustomerInfoTaskService;
import hn.com.tigo.equipmentinsurance.services.customerinfo.ParameterArray;
import hn.com.tigo.equipmentinsurance.services.customerinfo.ParameterType;
import hn.com.tigo.equipmentinsurance.services.customerinfo.TaskRequestType;
import hn.com.tigo.equipmentinsurance.services.customerinfo.TaskResponseType;

import java.util.Map;

@Service
public class CustomerInfoProvider {

	// Props

	public CustomerInfoProvider() {

	}

	public CustomerInfoModel getCustomerInfoByServiceAccount(final String serviceAccount) {
		CustomerInfoModel customerInfo = new CustomerInfoModel();
		CBSQueryCustomerInfoTaskService cBSQueryCustomerInfoTaskService = new CBSQueryCustomerInfoTaskService();
		CBSQueryCustomerInfoTask cBSQueryCustomerInfoTask = cBSQueryCustomerInfoTaskService
				.getCBSQueryCustomerInfoTaskPort();

		try {
			TaskResponseType taskResponseType = cBSQueryCustomerInfoTask
					.executeTask(this.buildTaskRequestTypeByAccountCode(serviceAccount));

			Gson gson = new Gson();
			MapperCBSQueryCustomerInfo mapper = null;

			for (ParameterType row : taskResponseType.getParameters().getParameter()) {
				mapper = gson.fromJson(row.getValue(), MapperCBSQueryCustomerInfo.class);
				break;
			}

			String customerName = null;

			// Verificamos si el serviceAccount termina en "NIHN".
			if (serviceAccount.endsWith("NIHN")) {
				customerName = mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getCustomer()
						.getOrgInfo().getOrgName();
			} else {
				String firstName = mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getCustomer()
						.getIndividualInfo().getFirstName();

				String lastName = mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getCustomer()
						.getIndividualInfo().getLastName();

				if (lastName != null) {
					customerName = firstName.concat(" ").concat(lastName);
				} else {
					customerName = firstName;
				}
			}

			customerInfo.setCustomerName(customerName);

		} catch (AdapterException_Exception e) {
			throw new BadRequestException(e.getMessage());
		}

		return customerInfo;
	}

	// Methods

	/**
	 * Se construye el request del Servicio Soap CBSQueryCustomerInfoTaskService por
	 * el AccountCode
	 *
	 * @return
	 */
	private TaskRequestType buildTaskRequestTypeByAccountCode(final String customerKey) {

		// Instanciamos la clase de retorno
		TaskRequestType taskRequestType = new TaskRequestType();

		// Instanciamos las clases que representan el json
		ParameterType parameterType = new ParameterType();
		ParameterArray parameterArray = new ParameterArray();

		QueryObj queryObj = new QueryObj();

		// Seteamos los valores
		CustAccessCode custAccessCode = new CustAccessCode();
		custAccessCode.setCustomerKey(customerKey);

		queryObj.setCustAccessCode(custAccessCode);

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

	public CustomerInfoModel getCustomerInfoByAcctCode(final String acctCode) {

		CustomerInfoModel customerInfo = new CustomerInfoModel();

		CBSQueryCustomerInfoTaskService cBSQueryCustomerInfoTaskService = new CBSQueryCustomerInfoTaskService();

		CBSQueryCustomerInfoTask cBSQueryCustomerInfoTask = cBSQueryCustomerInfoTaskService
				.getCBSQueryCustomerInfoTaskPort();

		try {

			TaskResponseType taskResponseType = cBSQueryCustomerInfoTask
					.executeTask(this.buildTaskRequestTypeByAcctCode(acctCode));

			Gson gson = new Gson();
			MapperCBSQueryCustomerInfo mapper = null;

			for (ParameterType row : taskResponseType.getParameters().getParameter()) {
				mapper = gson.fromJson(row.getValue(), MapperCBSQueryCustomerInfo.class);

				break;
			}

			customerInfo.setCustomerName(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult()
					.getAccount().getAcctInfo().getAcctBasicInfo().getAcctName());

			String customerAddress = null;

			Map<String, String> addressMap = mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult()
					.getAccount().getAcctInfo().getAddressInfoAsMap();

			String address1 = addressMap.get("Address1");
			String address2 = addressMap.get("Address2");
			String address3 = addressMap.get("Address3");
			String address4 = addressMap.get("Address4");

			customerAddress = address1 + ", " + address2 + ", " + address3 + ", " + address4;

			customerInfo.setCustomerAddress(customerAddress);

			String primaryIdentity = null;
			String rtn = null;
			for (AcctProperty property : mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult()
					.getAccount().getAcctInfo().getAcctBasicInfo().getAcctProperty()) {
				if ("C_ACCOUNT_INFO_3".equals(property.getCode())) {
					primaryIdentity = property.getValue();
				}

				if ("C_RTN".equals(property.getCode())) {
					rtn = property.getValue();
					break;
				}
			}
			customerInfo.setCustomerRtn(rtn);
			customerInfo.setPrimaryIdentity(primaryIdentity);
			customerInfo.setCustomerId(mapper.getQueryCustomerInfoResultMsg().getQueryCustomerInfoResult().getAccount()
					.getAcctInfo().getUserCustomer().getCustKey());


		} catch (AdapterException_Exception e) {
			throw new BadRequestException(e.getMessage());
		}

		return customerInfo;
	}

	// Methods

	/**
	 * Se construye el request del Servicio Soap CBSQueryCustomerInfoTaskService por
	 * el AccountCode
	 *
	 * @return
	 */
	private TaskRequestType buildTaskRequestTypeByAcctCode(final String accountCode) {

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
}
