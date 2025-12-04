package hn.com.tigo.equipmentblacklist.models;

public class GeneralResponseImeiModel {

	private ReturnData _return;

	public GeneralResponseImeiModel(ReturnData _return) {
		this._return = _return;
	}

	public ReturnData get_return() {
		return _return;
	}

	public void set_return(ReturnData _return) {
		this._return = _return;
	}

	public static class ReturnData {
		private GeneralResponse generalResponse;

		public ReturnData(GeneralResponse generalResponse) {
			this.generalResponse = generalResponse;
		}

		public GeneralResponse getGeneralResponse() {
			return generalResponse;
		}

		public void setGeneralResponse(GeneralResponse generalResponse) {
			this.generalResponse = generalResponse;
		}
	}

	public static class GeneralResponse {
		private int code;
		private String message;
		private String status;
		private String type;

		public GeneralResponse(int code, String message, String status, String type) {
			this.code = code;
			this.message = message;
			this.status = status;
			this.type = type;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}
