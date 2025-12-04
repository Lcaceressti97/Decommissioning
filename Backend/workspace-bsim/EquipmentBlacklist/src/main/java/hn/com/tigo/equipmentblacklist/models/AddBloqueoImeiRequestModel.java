package hn.com.tigo.equipmentblacklist.models;

import lombok.Data;

@Data
public class AddBloqueoImeiRequestModel {

	private String tipobloqueo;

	private HistoricalDetailModel detallebandit;
}
