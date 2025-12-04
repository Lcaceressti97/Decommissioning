package hn.com.tigo.comodatos.soap.request;

//Clase para realizar la busqueda de la promocion activa para las tablas cmd_promotions, cmd_promotions_details
public class PromotionsRequest {

	
	private String precio_promo;
	private String meses_permanencia;
	private String codigo_modelo;
	private String tipo_cliente;
	private String current_date;
	
	public String getPrecio_promo() {
		return precio_promo;
	}
	public void setPrecio_promo(String precio_promo) {
		this.precio_promo = precio_promo;
	}
	public String getMeses_permanencia() {
		return meses_permanencia;
	}
	public void setMeses_permanencia(String meses_permanencia) {
		this.meses_permanencia = meses_permanencia;
	}
	public String getCodigo_modelo() {
		return codigo_modelo;
	}
	public void setCodigo_modelo(String codigo_modelo) {
		this.codigo_modelo = codigo_modelo;
	}
	public String getTipo_cliente() {
		return tipo_cliente;
	}
	public void setTipo_cliente(String tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}
	public String getCurrent_date() {
		return current_date;
	}
	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}
	
}
