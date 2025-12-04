package hn.com.tigo.equipmentblacklist.utils;

public class Constants {

	public static final String DATASOURCE = "ASDCMMSSI";

	public static final String RECORDNOTFOUNDID = "Error getting record with invalid ID %s";

	public static final String RECORDNOTFOUNDPHONE = "No records found for PHONE %s";

	public static final String RECORDNOTFOUNDIMEI = "No records found for IMEI %s";

	public static final String RECORDNOTFOUNDACCTCODE = "No records found for BILLING ACCOUNT %s";

	public static final String RECORDNOTFOUNDESN = "No records found for SERIE %s";

	public static final String DATAINTREGITYVIOLATIONEXCEPTION = "Data Integrity Violation occurred while adding";

	public static final String BADREQUESTEXCEPTION = "Error occurred while adding: ";

	public static final String EXCEPTION = "Error occurred while adding: ";

	public static final String BADREQUESTEXCEPTIONUPDATE = "Error occurred while updating: ";

	public static final String EXCEPTIONUPDATE = "Error occurred while updating: ";

	public static final String BADREQUEST = "Bad Request";

	public static final String ERROR = "ERROR";
	
	public static final String ERRORADDINGBLOCKING = "Error occurred while adding blocking Imei: ";

	public static final String BLOCKEDLINE = "The line is already blocked";

	public static final String IMEIREQUIRED = "EsnImei is required";

	public static final String SERVICECOMPLETED = "Service Completed";

	public static final String OK = "OK";
	
	public static final String INFO = "INFO";
	
	public static final String INTERNALSERVERERROR = "Internal Server Error";

	public static final String ERRORADDINGREMOVEBLOCKING = "Error occurred while adding remove blocking Imei: ";

	public static final String UNLOCKEDLINEVALIDATION = "The line is already unblocked";
	
	public static final String LINEVALIDATIONDOESNOTEXIST = "Unlocking cannot be performed because the line does not exist";
	
	public static final String ERROR_HISTORICAL_DETAIL = "Error there is more than one detail with blocked status for this record: ";

}
