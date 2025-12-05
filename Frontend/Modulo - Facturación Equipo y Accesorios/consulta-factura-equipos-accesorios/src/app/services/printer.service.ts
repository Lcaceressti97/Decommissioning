import { Injectable } from '@angular/core';
import jsPDF from 'jspdf';

@Injectable({
  providedIn: 'root'
})
export class PrinterService {

  constructor() { }
  generatePdf(fileName: string): void {
    // Crear un nuevo documento PDF con dimensiones personalizadas (80x80 mm)
    const pdf = new jsPDF({
      orientation: 'portrait',
      unit: 'mm',
      format: [80, 80] // Ancho y alto en milímetros
    });

    // Agregar contenido al PDF
    pdf.setFontSize(12);
    pdf.text('Factura', 10, 10); // Título de la factura
    pdf.setFontSize(10);
    pdf.text('Cliente: Nombre del cliente', 10, 20); // Nombre del cliente
    pdf.text('Fecha: 15 de abril de 2024', 10, 25); // Fecha
    pdf.text('Total: $100.00', 10, 30); // Total
    pdf.setFontSize(8);
    pdf.text('Gracias por su compra', 10, 75); // Mensaje de agradecimiento

    // Obtener los datos del PDF como una cadena de datos URI
    const pdfDataUri = pdf.output('datauristring');

    // Abrir una nueva pestaña del navegador con el PDF
    window.open(pdfDataUri, '_blank');
  }


}
