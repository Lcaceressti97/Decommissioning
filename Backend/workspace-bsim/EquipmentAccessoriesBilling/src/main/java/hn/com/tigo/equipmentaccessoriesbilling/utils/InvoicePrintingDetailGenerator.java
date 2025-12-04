package hn.com.tigo.equipmentaccessoriesbilling.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceDetailEntity;

public class InvoicePrintingDetailGenerator {

	public static String generateInvoiceDetail(BillingEntity billing, BranchOfficesEntity branchOffice) {

		StringBuilder detalleFactura = new StringBuilder();

		// Encabezado de la factura
		detalleFactura.append("Telefónica Celular S.A. de C.V.\\CELTEL").append("\n\n");
		detalleFactura.append("Plaza Tigo Colonia Los Almendros al final del Blv. Morazan, Tegucigalpa").append("\n");
		detalleFactura.append("PBX:2265-8446, Fax: 2265-8446")
				.append("\n");
		detalleFactura.append("Rtn:").append(branchOffice.getRtn() == null ? "" : branchOffice.getRtn()).append("  Usuario: ").append(billing.getSeller() == null ? "" : billing.getSeller())
				.append("\n\n");

		// Número de Facturación
		detalleFactura.append("No. de Factura   ").append(billing.getNumberDei() == null ? "" : billing.getNumberDei()).append("\n\n\n");

		// CAI
		detalleFactura.append("CAI").append("\n\n");
		detalleFactura.append(billing.getCai() == null ? "" : billing.getCai()).append("\n");

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
		LocalDateTime fechaLimite = LocalDateTime.parse(billing.getDeadLine(), inputFormatter);
		LocalDate fechaSolo = fechaLimite.toLocalDate();
		String fechaLimiteFormateada = fechaSolo.format(DateTimeFormatter.ofPattern("dd MM yyyy"));

		detalleFactura.append("Fec.Limite de Emision: ").append(fechaLimiteFormateada).append("\n");
		detalleFactura.append("Rango Inicial: ")
				.append(billing.getInitialRank() == null ? "" : billing.getInitialRank()).append("\n");
		detalleFactura.append("Rango Final..: ").append(billing.getFinalRank() == null ? "" : billing.getFinalRank())
				.append("\n");

		// Datos del Adquiriente exonerado
		detalleFactura.append("Datos del Adquiriente exonerado\n");
		detalleFactura.append("Numero correlativo de la orden de\n");
		detalleFactura.append("compra Exenta:\n");
		detalleFactura
				.append(billing.getCorrelativeOrdenExemptNo() == null ? "" : billing.getCorrelativeOrdenExemptNo())
				.append("\n");
		detalleFactura.append("Numero correlativo de Constancia\n");
		detalleFactura.append("del Registro de Exonerados:\n");
		detalleFactura.append(
				billing.getCorrelativeCertificateExoNo() == null ? "" : billing.getCorrelativeCertificateExoNo())
				.append("\n");
		detalleFactura.append("Numero Carnet Diplomatico:\n");
		detalleFactura.append(billing.getDiplomaticCardNo() == null ? "" : billing.getDiplomaticCardNo()).append("\n");
		detalleFactura.append("Numero identificativo del Registro\n");
		detalleFactura.append("de la Secretaria de Estado en el\n");
		detalleFactura.append("despacho de Agricultura y Ganaderia:\n");
		detalleFactura.append("").append("\n\n");

		detalleFactura.append(branchOffice.getEmail() == null ? "" : branchOffice.getEmail()).append("\n");
		detalleFactura.append(branchOffice.getAddress() == null ? "" : branchOffice.getAddress()).append("\n");
		detalleFactura.append("PBX: ").append(branchOffice.getPbx() == null ? "" : branchOffice.getPbx()).append(", FAX: ").append(branchOffice.getFax() == null ? "" : branchOffice.getFax())
				.append("\n");
		LocalDateTime fechaCreacionDateTime = billing.getCreated();
		String horaFormateada = fechaCreacionDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		String fechaCreacionFormatiada = fechaCreacionDateTime.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
		detalleFactura.append("Cajero: ").append(billing.getCashierName()  == null ? "" : billing.getCashierName()).append("  Hora: ").append(horaFormateada)
				.append("\n");

		// Documento FS4
		String fechaVencimientoFormatiada = billing.getDueDate().format(DateTimeFormatter.ofPattern("dd MM yyyy"));
		detalleFactura.append("Documento ").append(billing.getInvoiceType()  == null ? "" : billing.getInvoiceType()).append("  ").append(billing.getId() == null ? "" : billing.getId())
				.append("\n");
		detalleFactura.append("Fecha: ").append(fechaCreacionFormatiada).append("  ").append("Vence: ")
				.append(fechaVencimientoFormatiada).append("\n");
		detalleFactura.append("Bodega: ").append(billing.getWarehouse() == null ? "" : billing.getWarehouse()).append("\n");
		detalleFactura.append("Cuenta . : ").append(billing.getAcctCode() == null ? "" : billing.getAcctCode()).append(" Tel: ")
				.append(billing.getPrimaryIdentity()).append("\n");
		detalleFactura.append("Nombre.: ").append(billing.getCustomer() == null ? "" : billing.getCustomer()).append("\n\n");

		// Dirección y datos del cliente
		detalleFactura.append("Dirección: ").append(billing.getCustomerAddress() == null ? "" : billing.getCustomerAddress()).append("\n");
		detalleFactura.append("RTN/ID: ").append(billing.getCustomerRtnId() == null ? "" : billing.getCustomerRtnId())
				.append("\n");

		// Credito / Contado
		detalleFactura.append("Credito/Contado:\n");
		detalleFactura.append("Vendedor: ").append(billing.getCashierName() == null ? "" : billing.getCashierName())
				.append("\n");
		detalleFactura.append("Cuota Inicial .: ").append("         0.00").append("\n");
		detalleFactura.append("1 mes de: ").append("          0.00").append("\n\n");

		// Detalles de la factura
		detalleFactura.append(String.format("%-6s %-10s %-8s %-8s\n", "Cant.", "Artic. T", "Precio", "Valor"));
		detalleFactura.append("Descripcion\n");
		detalleFactura.append("*--------------------------------------------------*\n");
		List<InvoiceDetailEntity> detalles = billing.getInvoiceDetails();
		for (InvoiceDetailEntity detalle : detalles) {
		    detalleFactura.append(String.format("%-6.1f %-10s %-8.2f %-8.2f\n", 
		        detalle.getQuantity(), 
		        detalle.getModel(), 
		        detalle.getUnitPrice(), 
		        detalle.getSubtotal()));
		    
		    // Mueve la descripción a una nueva línea
		    detalleFactura.append(detalle.getDescription()).append("\n");
		    
		    detalleFactura.append("E.S.N. NO.: ")
		            .append(detalle.getSerieOrBoxNumber() == null || detalle.getSerieOrBoxNumber().isEmpty() ? " " : detalle.getSerieOrBoxNumber())
		            .append("\n");
		    detalleFactura.append("*--------------------------------------------------*\n");
		}

		double valoresGravables = billing.getSubtotal() - billing.getDiscount();

		// Subtotal, descuentos, impuestos y total
		detalleFactura.append("Subtotal....................:  $").append("            ").append(String.format("%.2f", billing.getSubtotal()))
				.append("\n");
		detalleFactura.append("( - ) Descuentos y Rebajas").append("\n");
		detalleFactura.append("Otorgados.................: $").append("             ").append(String.format("%.2f", billing.getDiscount()))
				.append("\n");
		detalleFactura.append("*--------------------------------------------------*\n");
		detalleFactura.append("Valores Exento..........: $").append("            ")
				.append(billing.getExemptAmount() == null ? "0.00" : String.format("%.2f", billing.getExemptAmount())).append("\n");
		detalleFactura.append("Valores Exonerado....: $").append("            ")
				.append("0.00" ).append("\n");
		detalleFactura.append("Valores Alicuota\n");
		detalleFactura.append("Tasa Cero..................: $").append("            ")
				.append("0.00").append("\n");
		detalleFactura.append("Valores Gravables\n");
		detalleFactura.append("15% ISV.....................: $").append("           ").append(String.format("%.2f", valoresGravables))
				.append("\n");
		detalleFactura.append("Impuesto Sobre Venta\n");
		detalleFactura.append("15% sobre Valores\n");
		detalleFactura.append("Gravables..................: $").append("           ")
				.append(String.format("%.2f", billing.getAmountTax())).append("\n");
		detalleFactura.append("Impuesto Sobre Venta\n");
		detalleFactura.append("15% sobre Valores\n");
		detalleFactura.append("Alicuota Tasa Cero....: $").append("            ").append("0.00\n");
		detalleFactura.append("Total Factura.............: $").append("            ").append(String.format("%.2f", billing.getAmountTotal()))
				.append("\n\n");

		// Tasa de Cambio
		detalleFactura.append("Tasa de Cambio: ").append(billing.getExchangeRate()).append("\n\n");

		// Total Lempiras
		double totalLempiras = billing.getAmountTotal() * billing.getExchangeRate();
		detalleFactura.append("Tot.Fact. Lempiras     L").append("             ")
				.append(String.format("%.2f", totalLempiras)).append("\n");
		detalleFactura.append("Total en Letras:").append("\n");
		String totalEnLetras = convertirALetras(totalLempiras);
		detalleFactura.append(totalEnLetras.toUpperCase()).append("\n\n\n");

		// Texto adicional
		detalleFactura.append("\"Reconozco y acepto que la unica garant-\n");
		detalleFactura.append("ia sobre este equipo es la otorgada por\n");
		detalleFactura.append("el Fabricante, liberando a CELTEL, S.A.\n");
		detalleFactura.append("DE C.V. de cualquier responsabilidad\".\n\n");
		detalleFactura.append("Tiene 5 días habiles para hacer reclam-\n");
		detalleFactura.append("os por desperfecto de fabrica. Recuerda\n");
		detalleFactura.append("presentarte a la agencia con el equipo \n");
		detalleFactura.append("como fue entregado. Reclamos posteriores\n");
		detalleFactura.append("5 días habiles favor visita CETREL.\n\n\n");

		// Firma y sello de recibo
		detalleFactura.append("--------------------------------------------------\n");
		detalleFactura.append("Firma y sello de recibido\n");
		detalleFactura.append("Original: Cliente\n");
		detalleFactura.append("Copia:Obligado Tributario Emisor\n");

		// Convertir el detalle de la factura a Base64
		String facturaEnTexto = detalleFactura.toString();
		byte[] facturaEnBytes = facturaEnTexto.getBytes();
		String facturaEnBase64 = Base64.getEncoder().encodeToString(facturaEnBytes);

		return facturaEnBase64;
	}

	public static String convertirALetras(double totalLempiras) {
		String[] unidadesTxt = { "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez",
				"once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve" };
		String[] decenasTxt = { "", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta",
				"noventa" };
		String[] centenasTxt = { "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos",
				"seiscientos", "setecientos", "ochocientos", "novecientos" };

		long parteEntera = (long) totalLempiras;
		double parteDecimal = totalLempiras - parteEntera;

		StringBuilder resultado = new StringBuilder();

		if (parteEntera == 0) {
			resultado.append("cero");
		} else {
			// Procesar la parte entera
			long miles = parteEntera / 1000;
			int centenas = (int) (parteEntera / 100) % 10;
			int decenas = (int) (parteEntera / 10) % 10;
			int unidades = (int) parteEntera % 10;

			if (miles > 0) {
				resultado.append(convertirALetras(miles)).append(" mil ");
			}

			if (centenas > 0 || decenas > 0 || unidades > 0) {
				if (centenas == 1 && decenas == 0 && unidades == 0) {
					resultado.append("cien");
				} else {
					resultado.append(centenasTxt[centenas]);
					if (decenas >= 2) {
						resultado.append(" ").append(decenasTxt[decenas]);
						if (unidades > 0) {
							resultado.append(" y ").append(unidadesTxt[unidades]);
						}
					} else if (decenas == 1) {
						resultado.append(" ").append(unidadesTxt[decenas * 10 + unidades]);
					} else if (unidades > 0) {
						resultado.append(" ").append(unidadesTxt[unidades]);
					}
				}
			}
		}

		// Procesar la parte decimal
		if (parteDecimal > 0) {
			int decimales = (int) (parteDecimal * 100);
			resultado.append(" con ").append(decimales).append("/100");
		}

		return resultado.toString().trim();
	}

}
