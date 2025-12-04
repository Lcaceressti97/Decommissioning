package hn.com.tigo.equipmentblacklist.models;

import lombok.Data;

@Data
public class RemoveBloqueoImeiRequestModel {

	private String tipobloqueo;

	private String imei;

	private String simcard;
	private UpdateDetailBanditUnlockModel actualizarDetalleBanditDesbloqueo;
}
