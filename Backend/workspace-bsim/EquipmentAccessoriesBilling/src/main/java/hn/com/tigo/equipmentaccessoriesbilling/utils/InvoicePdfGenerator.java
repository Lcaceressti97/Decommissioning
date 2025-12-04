package hn.com.tigo.equipmentaccessoriesbilling.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceDetailEntity;

public class InvoicePdfGenerator {

    public static byte[] generateInvoicePdf(BillingEntity billing, BranchOfficesEntity branchOffice, String cashierName) {
        float boxWidth = 200; // Ancho del cuadro ajustado
        float boxMargin = 5; // Margen del cuadro ajustado
        float titleFontSize = 8f; // Tamaño de fuente del título ajustado
        float contentFontSize = 6f; // Tamaño de fuente del contenido ajustado
        // Decodificar la imagen base64
        byte[] imageBytes = Base64.getDecoder().decode(Constants.LOGO);

        try (PDDocument document = new PDDocument()) {
            float currentY = 800; // Posición Y inicial
            int pageNumber = 1;

            // Definir el tamaño de la factura (80 mm de ancho, altura inicial 297 mm como referencia)
            PDRectangle receiptSize = new PDRectangle(226, 842); // 80mm x 297mm en puntos

            PDPage page = new PDPage(receiptSize);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Crear objeto de imagen desde bytes
            PDImageXObject logo = PDImageXObject.createFromByteArray(document, imageBytes, "logo");

            // Dibujar imagen en la esquina superior izquierda
            float logoWidth = 50; // Ajusta el ancho del logo según sea necesario
            float logoHeight = 40; // Ajusta la altura del logo según sea necesario
            float logoX = 10; // Ajusta la posición X del logo
            float logoY = page.getMediaBox().getHeight() - logoHeight - 10; // Ajusta la posición Y del logo
            contentStream.drawImage(logo, logoX, logoY, logoWidth, logoHeight);

            // Agregar el título
            currentY -= 30;
            PDType1Font titleFont = PDType1Font.HELVETICA_BOLD;
            // Texto del título
            String titleText = "DETALLE DE FACTURA:";
            String numberDeiText = billing.getNumberDei() == null ? "" : billing.getNumberDei();

            // Posicionar el título
            float titleX = logoX + logoWidth + 23; // Alineado a la derecha del logo
            float titleY = logoY + (logoHeight / 2) + (titleFontSize / 2); // Centrado con respecto al logo

            // Escribir el título
            writeTextTitle(contentStream, titleText, titleX, titleY, titleFont, titleFontSize);

            // Ajustar Y para la siguiente línea (Número DEI)
            float numberDeiY = titleY - titleFontSize - 3; // Espacio de 3 puntos entre líneas

            // Escribir el número DEI
            writeTextTitle(contentStream, numberDeiText, titleX, numberDeiY, titleFont, titleFontSize);

            // Definir el cuadro
            float boxX = (page.getMediaBox().getWidth() - boxWidth) / 2;
            float boxY = 50; // Posición Y del cuadro desde abajo
            float boxHeight = currentY - boxY;

            // Dibujar el cuadro
            drawBox(contentStream, boxX, boxY, boxWidth, boxHeight);

            // Encabezado de la factura
            currentY -= 20;
            currentY = writeText(contentStream, "Telefónica Celular S.A. de C.V.\\CELTEL", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeTextWrapped(contentStream,"Plaza Tigo Colonia Los Almendros al final del Blv. Morazan, Tegucigalpa",
                    boxX + boxMargin, currentY - 15, PDType1Font.HELVETICA, contentFontSize, boxWidth - 2 * boxMargin);
            currentY = writeText(contentStream, "PBX:2265-8446, Fax: 2265-8446",
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream,
                    "Rtn: " + (branchOffice.getRtn() == null ? "" : branchOffice.getRtn()) + "  Usuario: "
                            + (billing.getSeller() == null ? "" : billing.getSeller()),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            // Número de Facturación
            currentY = writeText(contentStream,
                    "No. de Factura                    " + (billing.getNumberDei() == null ? "" : billing.getNumberDei()),
                    boxX + boxMargin, currentY - 15, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "CAI", boxX + boxMargin, currentY - 20, PDType1Font.HELVETICA,
                    contentFontSize);

            currentY = writeText(contentStream, billing.getCai() == null ? "" : billing.getCai(), boxX + boxMargin,
                    currentY - 20, PDType1Font.HELVETICA, contentFontSize);

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
            LocalDateTime fechaLimite = LocalDateTime.parse(billing.getDeadLine(), inputFormatter);
            LocalDate fechaSolo = fechaLimite.toLocalDate();
            String fechaLimiteFormateada = fechaSolo.format(DateTimeFormatter.ofPattern("dd MM yyyy"));

            currentY = writeText(contentStream, "Fec. Limite de Emision: " + fechaLimiteFormateada, boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    "Rango Inicial: " + (billing.getInitialRank() == null ? "" : billing.getInitialRank()),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    "Rango Final..: " + (billing.getFinalRank() == null ? "" : billing.getFinalRank()), boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);

            // Datos del Adquiriente exonerado
            currentY = writeText(contentStream, "Datos del Adquiriente exonerado", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Numero correlativo de la orden de compra Exenta:", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    billing.getCorrelativeOrdenExemptNo() == null ? "" : billing.getCorrelativeOrdenExemptNo(),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Numero correlativo de Constancia del Registro de Exonerados:", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    billing.getCorrelativeCertificateExoNo() == null ? "" : billing.getCorrelativeCertificateExoNo(),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Numero Carnet Diplomatico:", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    billing.getDiplomaticCardNo() == null ? "" : billing.getDiplomaticCardNo(), boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Numero identificativo del Registro de la Secretaria de Estado en el", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "despacho de Agricultura y Ganaderia:", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            // Información de contacto de la sucursal
            currentY = writeText(contentStream, branchOffice.getEmail() == null ? "" : branchOffice.getEmail(),
                    boxX + boxMargin, currentY - 15, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeTextWrapped(contentStream,(branchOffice.getAddress() == null ? "" : branchOffice.getAddress()),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize, boxWidth - 2 * boxMargin);

            currentY = writeText(contentStream,
                    "PBX: " + (branchOffice.getPbx() == null ? "" : branchOffice.getPbx()) + ", FAX: "
                            + (branchOffice.getFax() == null ? "" : branchOffice.getFax()),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);



            LocalDateTime fechaCreacionDateTime = billing.getCreated();
            String horaFormateada = fechaCreacionDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String fechaCreacionFormatiada = fechaCreacionDateTime.format(DateTimeFormatter.ofPattern("dd MM yyyy"));

            currentY = writeText(
                    contentStream, "Cajero: " + (cashierName == null ? "" : cashierName)
                            + "  Hora: " + horaFormateada,
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            // Documento FS4
            String fechaVencimientoFormatiada = billing.getDueDate().format(DateTimeFormatter.ofPattern("dd MM yyyy"));

            // Verifica si el tipo de factura es uno de los permitidos
            String amountText;
            if ("FC1".equals(billing.getInvoiceType()) ||
                    "FC3".equals(billing.getInvoiceType()) ||
                    "FS1".equals(billing.getInvoiceType()) ||
                    "FS3".equals(billing.getInvoiceType()) ||
                    "SHP".equals(billing.getInvoiceType()) ||
                    "SHO".equals(billing.getInvoiceType()) ||
                    "Facturación por Reclamo de Seguros".equals(billing.getFiscalProcess())) {
                // Si es uno de los tipos permitidos, usa amountTotal
                amountText = String.valueOf(billing.getAmountTotal());
            } else {
                amountText = "0.00";

            }

            String fechaVencimiento;
            if ("FC1".equals(billing.getInvoiceType()) ||
                    "FC3".equals(billing.getInvoiceType()) ||
                    "FS1".equals(billing.getInvoiceType()) ||
                    "FS3".equals(billing.getInvoiceType()) ||
                    "SHP".equals(billing.getInvoiceType()) ||
                    "SHO".equals(billing.getInvoiceType())) {
                // Si es uno de los tipos permitidos, usa amountTotal
                fechaVencimiento = fechaCreacionFormatiada;
            } else {
                fechaVencimiento = fechaVencimientoFormatiada;

            }

            currentY = writeText(contentStream,
                    "Documento: " + (billing.getInvoiceType() == null ? "" : billing.getInvoiceType()) + "  "
                            + (billing.getId() == null ? "" : billing.getId()),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    "Fecha: " + fechaCreacionFormatiada + " Vence: " + fechaVencimiento, boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    "Bodega: " + (billing.getWarehouse() == null ? "" : billing.getWarehouse()), boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    "Cuenta . : " + (billing.getAcctCode() == null ? "" : billing.getAcctCode()) + " Tel: "
                            + (billing.getPrimaryIdentity() == null ? "" : billing.getPrimaryIdentity()),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream,
                    "Nombre.: " + (billing.getCustomer() == null ? "" : billing.getCustomer()), boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);


            // Dirección y datos del cliente
            currentY -= 10;
            currentY = writeTextWrapped(contentStream,
                    "Dirección: " + (billing.getCustomerAddress() == null ? "" : billing.getCustomerAddress()),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize, boxWidth - 2 * boxMargin);

            currentY = writeText(contentStream,
                    "RTN/ID: " + (billing.getCustomerRtnId() == null ? "" : billing.getCustomerRtnId()),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);


            double valoresGravables;
            String creditoContado;
            if ("Facturación por Reclamo de Seguros".equals(billing.getFiscalProcess())) {
                valoresGravables = billing.getSubtotal() - billing.getExemptAmount();
                creditoContado = "CONTRA ENTREGA (SIN INT";
            } else {
                valoresGravables = billing.getSubtotal() - billing.getDiscount();
                creditoContado = "";
            }

            // Credito / Contado
            currentY = writeText(contentStream, "Credito/Contado: " + creditoContado, boxX + boxMargin, currentY, PDType1Font.HELVETICA,
                    contentFontSize);
            currentY = writeText(contentStream,
                    "Vendedor: " + (billing.getCashierName() == null ? "" : billing.getCashierName()), boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "Cuota Inicial .: " + "         0.00", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);


            // Escribe el texto en el PDF
            currentY = writeText(contentStream, "1 mes de: " + "          " + amountText, boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            // Detalles de la factura
            currentY = writeText(contentStream, "Cant.        Artic. T        Precio        Valor", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "Descripcion", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "*--------------------------------------------------*",
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            List<InvoiceDetailEntity> details = billing.getInvoiceDetails();
            for (InvoiceDetailEntity detail : details) {
                // Verifica si hay suficiente espacio para escribir el detalle del producto
                float requiredSpace = contentFontSize + 20; // Espacio requerido para el detalle del producto y un margen

                if (currentY < requiredSpace) {
                    // Si no hay suficiente espacio, agrega una nueva página
                    currentY = 770; // Reiniciar la posición Y
                    pageNumber++;
                    page = new PDPage(receiptSize);
                    document.addPage(page);
                    contentStream.close();
                    contentStream = new PDPageContentStream(document, page);
                    drawBox(contentStream, boxX, boxY, boxWidth, boxHeight);

                    // Redibujar el encabezado en la nueva página
                    currentY = writeText(contentStream, "Cant.        Artic. T        Precio       Valor", boxX + boxMargin,
                            currentY, PDType1Font.HELVETICA, contentFontSize);
                    currentY = writeText(contentStream, "Descripcion", boxX + boxMargin, currentY,
                            PDType1Font.HELVETICA_BOLD, contentFontSize);
                    currentY = writeText(contentStream, "*--------------------------------------------------*",
                            boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);
                }

                // Escribir detalles del producto
                currentY = writeText(contentStream,
                        String.format("%-14s%-18s%-16.2f%-18.2f", detail.getQuantity(), detail.getModel(),
                                detail.getUnitPrice(), detail.getSubtotal()),
                        boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);
                currentY = writeText(contentStream, detail.getDescription(), boxX + boxMargin, currentY,
                        PDType1Font.HELVETICA, contentFontSize);
                currentY = writeText(contentStream,
                        "E.S.N. NO.: "
                                + (detail.getSerieOrBoxNumber() == null || detail.getSerieOrBoxNumber().isEmpty() ? " "
                                : detail.getSerieOrBoxNumber()),
                        boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);
            }


            // Subtotal, descuentos, impuestos y total
            currentY = writeText(contentStream, "*--------------------------------------------------*",
                    boxX + boxMargin, currentY - 25, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream,
                    "Subtotal....................: $" + "                   "
                            + (billing.getSubtotal() == null ? "0.00" : String.format("%.2f", billing.getSubtotal())),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "( - ) Descuentos y Rebajas", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream,
                    "Otorgados.................: $" + "                   "
                            + (billing.getDiscount() == null ? "0.00" : String.format("%.2f", billing.getDiscount())),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "*--------------------------------------------------*",
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Valores Exento..........: $" + "                  "
                            + (billing.getExemptAmount() == null ? "0.00" : String.format("%.2f", billing.getExemptAmount())),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream,
                    "Valores Exonerado....: $" + "                  "
                            + ("0.00"),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Valores Alicuota", boxX + boxMargin, currentY, PDType1Font.HELVETICA,
                    contentFontSize);
            currentY = writeText(contentStream,
                    "Tasa Cero..................: $" + "                  "
                            + ("0.00"),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Valores Gravables", boxX + boxMargin, currentY, PDType1Font.HELVETICA,
                    contentFontSize);
            currentY = writeText(contentStream,
                    "15% ISV.....................: $" + "                  "
                            + (billing.getSubtotal() == null ? "0.00" : String.format("%.2f", valoresGravables)),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Impuesto Sobre Venta", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "15% sobre Valores", boxX + boxMargin, currentY, PDType1Font.HELVETICA,
                    contentFontSize);
            currentY = writeText(contentStream,
                    "Gravables..................: $" + "                  "
                            + (billing.getAmountTax() == null ? "0.00" : String.format("%.2f", billing.getAmountTax())),
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Impuesto Sobre Venta", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "15% sobre Valores", boxX + boxMargin, currentY, PDType1Font.HELVETICA,
                    contentFontSize);
            currentY = writeText(contentStream, "Alicuota Tasa Cero....: $" + "                  " + "0.00",
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream,
                    "Total Factura.............: $" + "                  " + String.format("%.2f", billing.getAmountTotal()), boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);

            if (currentY < 200) {
                currentY = 770;
                pageNumber++;
                page = new PDPage(receiptSize);
                document.addPage(page);
                contentStream.close();
                contentStream = new PDPageContentStream(document, page);
                drawBox(contentStream, boxX, boxY, boxWidth, boxHeight);
            }

            // Tasa de Cambio
            currentY = writeText(contentStream, "Tasa de Cambio: " + (billing.getExchangeRate() == null ? 0.00 : billing.getExchangeRate()), boxX + boxMargin,
                    currentY - 10, PDType1Font.HELVETICA, contentFontSize);

            // Total Lempiras
            currentY -= 10;
            String totalLpsString = billing.getTotalLps();
            Double totalLps = null;

            if (totalLpsString != null && !totalLpsString.isEmpty()) {
                try {
                    totalLps = Double.parseDouble(totalLpsString);
                } catch (NumberFormatException e) {
                    // Manejo de error: el String no es un número válido
                    totalLps = 0.0;
                }
            }

            // Formatear el valor a dos decimales
            String totalLpsFormatted = (totalLps == null) ? "0.00" : String.format("%.2f", totalLps);
            currentY = writeText(contentStream,
                    "Tot.Fact. Lempiras     L" + "             " + totalLpsFormatted,
                    boxX + boxMargin, currentY, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "Total en Letras:", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            currentY = writeTextWrapped(contentStream, billing.getTotalLpsLetters() == null ? "" : billing.getTotalLpsLetters(), boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize, boxWidth - 2 * boxMargin);

            currentY -= 15;

            // Texto adicional
            currentY = writeText(contentStream, "\"Reconozco y acepto que la unica garantia sobre este equipo es la", boxX + boxMargin,
                    currentY , PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "otorgada por el Fabricante, liberando a CELTEL, S.A. DE C.V. de", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "cualquier responsabilidad\".", boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);

            currentY = writeText(contentStream, "Tiene 5 días habiles para hacer reclamos por desperfecto de", boxX + boxMargin,
                    currentY - 10, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "fabrica. Recuerda presentarte a la agencia con el equipo como", boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "fue entregado. Reclamos posteriores 5 días habiles favor visita", boxX + boxMargin,
                    currentY, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "CETREL.", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            // Firma y sello de recibo
            currentY = writeText(contentStream, "--------------------------------------------------", boxX + boxMargin,
                    currentY - 50, PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "Firma y sello de recibido", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "Original: Cliente", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);
            currentY = writeText(contentStream, "Copia: Obligado Tributario Emisor", boxX + boxMargin, currentY,
                    PDType1Font.HELVETICA, contentFontSize);

            contentStream.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            byte[] pdfBytes = baos.toByteArray();
            document.close();

            return pdfBytes;
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el PDF de la factura", e);
        }
    }

    private static float writeText(PDPageContentStream contentStream, String text, float x, float y, PDType1Font font,
                                   float fontSize) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
        return y - fontSize - 2; // Ajustar la posición Y para la siguiente línea de texto
    }

    private static void writeTextTitle(PDPageContentStream contentStream, String text, float x, float y, PDFont font,
                                       float fontSize) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    private static void drawBox(PDPageContentStream contentStream, float x, float y, float width, float height)
            throws IOException {
        contentStream.setLineWidth(1); // Ancho del borde del cuadro
        contentStream.addRect(x, y, width, height);
        contentStream.stroke();
    }

    private static float writeTextWrapped(PDPageContentStream contentStream, String text, float x, float y, PDType1Font font, float fontSize, float maxWidth) throws IOException {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        float spaceWidth = font.getStringWidth(" ") / 1000 * fontSize;

        for (String word : words) {
            float lineWidth = font.getStringWidth(line + word) / 1000 * fontSize;

            if (line.length() > 0) {
                lineWidth += spaceWidth; // Añadir el ancho del espacio para la siguiente palabra
            }

            if (lineWidth > maxWidth) {
                // Escribir la línea actual y reiniciar
                contentStream.beginText();
                contentStream.setFont(font, fontSize);
                contentStream.newLineAtOffset(x, y);
                contentStream.showText(line.toString());
                contentStream.endText();

                y -= fontSize + 2; // Mover hacia abajo para la siguiente línea
                line = new StringBuilder(word); // Comenzar nueva línea con la palabra actual
            } else {
                line.append(line.length() > 0 ? " " : "").append(word); // Añadir la palabra a la línea
            }
        }

        // Escribir la última línea
        if (line.length() > 0) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(line.toString());
            contentStream.endText();
        }

        return y - fontSize - 2;
    }
}