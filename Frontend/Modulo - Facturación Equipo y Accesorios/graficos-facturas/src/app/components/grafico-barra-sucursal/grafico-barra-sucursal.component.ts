import { Component, Input, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { ContentResponse, InvoiceStatistics } from 'src/app/model/model';

@Component({
  selector: 'app-grafico-barra-sucursal',
  templateUrl: './grafico-barra-sucursal.component.html',
  styleUrls: ['./grafico-barra-sucursal.component.css']
})
export class GraficoBarraSucursalComponent implements OnInit {

  // Props
  // Inputs
  @Input() inputData: InvoiceStatistics;
  colors: string[] = [];
  // Elements HTML
  elementGraph: any = null;

  // Elemento CANVAS
  elementGraphType: any = null;

  constructor() { }

  ngOnInit(): void {
    this.addColor();
    this.buildGraphics();
  }

  addColor() {
    for (let i = 0; i <= 7; i++) {
      this.colors.push(this.getRandomColor());
    }
  }

    /**
* Método encargado de crear las gráficas
* 
*/
async buildGraphics() {



  // Obtenemos los elementos por el id 
  this.elementGraph = document.getElementById('graf_branche_offices') as HTMLCanvasElement;

  this.buildGraphicsCircleAll();

}


  /**
* Método encargado de construir gráficos circulares
* 
*/
buildGraphicsCircleAll(): void {
  // Contenido del gráfico de barra
  let arrayPendiente: number[] = [];
  let arrayAutorizada: number[] = [];
  let arrayEmitida: number[] = [];
  let arrayAnuladaSinEmitir: number[] = [];
  let arrayAnuladaConNumeroFiscal: number[] = [];
  let arrayError: number[] = [];

  const RESULT_MAP = {};

  this.inputData.invoicesByBranchOfficeAndStatus.forEach(item => {
    const { branchOfficeName, status, count } = item;

    // Verificar si ya existe una entrada para el tipo de factura en el mapa
    if (!RESULT_MAP[branchOfficeName]) {
      RESULT_MAP[branchOfficeName] = {};
    }

    // Agregar el recuento al estado correspondiente dentro del tipo de factura
    RESULT_MAP[branchOfficeName][status] = count;
  });

  Object.keys(RESULT_MAP).forEach((type) => {
    const valueFC = RESULT_MAP[type];

    arrayPendiente.push('unauthorizedInvoices' in valueFC ? valueFC.unauthorizedInvoices : 0);
    arrayAutorizada.push('authorizedInvoices' in valueFC ? valueFC.authorizedInvoices : 0);
    arrayEmitida.push('issuedInvoices' in valueFC ? valueFC.issuedInvoices : 0);
    arrayAnuladaSinEmitir.push('canceledInvoicesWithoutIssued' in valueFC ? valueFC.canceledInvoicesWithoutIssued : 0);
    arrayAnuladaConNumeroFiscal.push('canceledInvoicesWithTaxNumber' in valueFC ? valueFC.canceledInvoicesWithTaxNumber : 0);
    arrayError.push('errorInvoice' in valueFC ? valueFC.errorInvoice : 0);
  });

  // Calcular el máximo y mínimo de los datos
  const allDataArrays = [arrayPendiente, arrayAutorizada, arrayEmitida, arrayAnuladaSinEmitir, arrayAnuladaConNumeroFiscal, arrayError];
  const flatData = allDataArrays.reduce((acc, val) => acc.concat(val), []);
  const maxValue = Math.max(...flatData);
  const minValue = Math.min(...flatData);

  // // Construyendo gráficas
  this.elementGraphType = new Chart(this.elementGraph, {
    type: 'bar',
    data: {
      labels: Object.keys(RESULT_MAP),
      datasets: [
        {
          label: "Pendientes",
          backgroundColor: this.getRandomColor(),
          data: arrayPendiente
        },
        {
          label: "Autorizadas",
          backgroundColor: this.getRandomColor(),
          data: arrayAutorizada
        },
        {
          label: "Emitidas",
          backgroundColor: this.getRandomColor(),
          data: arrayEmitida
        },
        {
          label: "Anulada Sin Emitir",
          backgroundColor: this.getRandomColor(),
          data: arrayAnuladaSinEmitir
        },
        {
          label: "Anulada Con Número Fiscal",
          backgroundColor: this.getRandomColor(),
          data: arrayAnuladaConNumeroFiscal
        },
        {
          label: "Error",
          backgroundColor: this.getRandomColor(),
          data: arrayError
        },
      ]
    },
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true,
            min: minValue > 0 ? 0 : minValue, // Asegura que el mínimo sea 0 si todos los valores son positivos
            max: Math.ceil(maxValue * 1.1), // Un poco más que el máximo para que se vea bien
            stepSize: 1 // Ajusta el tamaño del paso si es necesario
          }
        }]
      }
    },
  });

  this.elementGraphType.update();
}


  getRandomColor() {
    return `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.7)`;
  }


}
