package hn.com.tigo.equipmentinsurance.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalDetailModel {

	private Long id;

	@NotNull(message = "ID_HISTORICAL cannot be null")
	private Long idHistorical;

	@NotNull(message = "ESN_IMEI cannot be null")
	@NotBlank(message = "The ESN_IMEI is required.")
	@Size(min = 1, max = 40, message = "size must be between 1 and 40")
	private String esnImei;

	private String ivesn;

	private Long operador;

	@NotNull(message = "LOCK_TYPE cannot be null")
	@NotBlank(message = "The LOCK_TYPE is required.")
	private String tipobloqueo;

	@NotNull(message = "PHONE cannot be null")
	@NotBlank(message = "The PHONE is required.")
	@Size(min = 8, max = 12, message = "The PHONE must be from 8 to 12 characters.")
	private String telefono;

	@NotNull(message = "ACCT_CODE cannot be null")
	private Long anexo;

	private String technologyType;

	private String simcard;

	private String nombreUsuarioTransaccion;

	private String identidadUsuarioTransaccion;

	private String direccionUsuarioTransaccion;

	private String telefonoUsuarioTransaccion;

	private String motivoBloqueo;

	private LocalDateTime createdDate;

	private String motivoTransaccion;

	private String nombreDesbloqueante;

	private String identidadDesbloqueante;

	private String direccionDesbloqueante;

	private String telefonoDesbloqueante;

	private String motivoDesbloqueo;

	private LocalDateTime fechaDesbloqueo;

	private String usuarioTransaccion;

	private String pantallaTransaccion;

	private String estadoBajaAlta;

	private Long status;

	private int anoTransaccion;

	private Long mesTransaccion;

	private Long diaTransaccion;

	private Long horaTransaccion;

	private LocalDateTime fechaBloqueo;
	
	private String modelo;
}
