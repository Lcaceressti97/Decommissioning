import { Component, Input, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { ContentBar, ContentResponse, DataGraph, InvoiceStatistics, InvoicesByTypeAndStatus } from 'src/app/model/model';

@Component({
  selector: 'app-grafico-barra-tipo-estado',
  templateUrl: './grafico-barra-tipo-estado.component.html',
  styleUrls: ['./grafico-barra-tipo-estado.component.css']
})
export class GraficoBarraTipoEstadoComponent implements OnInit {

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
    this.elementGraph = document.getElementById('graf_status') as HTMLCanvasElement;

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
    //let arrayConFiscal: number[] = [];
    let arrayAnuladaSinEmitir: number[] = [];
    let arrayAnuladaConNumeroFiscal: number[] = [];
    let arrayError: number[] = [];

    // 
    const RESULT_MAP = {};

    this.inputData.invoicesByTypeAndStatus.forEach(item => {
      const { invoiceType, status, count } = item;

      // Verificar si ya existe una entrada para el tipo de factura en el mapa
      if (!RESULT_MAP[invoiceType]) {
        RESULT_MAP[invoiceType] = {};
      }
    
      // Agregar el recuento al estado correspondiente dentro del tipo de factura
      RESULT_MAP[invoiceType][status] = count;
    });




    Object.keys(RESULT_MAP).map((type, index, array) => {

      // Mapeamos el objeto
      const valueFC: ContentResponse = RESULT_MAP[type];
     

      arrayPendiente.push('unauthorizedInvoices' in RESULT_MAP[type]? valueFC.unauthorizedInvoices : 0);

      arrayAutorizada.push('authorizedInvoices' in RESULT_MAP[type] ? valueFC.authorizedInvoices : 0);

      arrayEmitida.push('issuedInvoices' in RESULT_MAP[type]? valueFC.issuedInvoices : 0);

      //arrayConFiscal.push('invoicesWithTaxNumber' in RESULT_MAP[type]? valueFC.invoicesWithTaxNumber : 0);

      arrayAnuladaSinEmitir.push('canceledInvoicesWithoutIssued' in RESULT_MAP[type]? valueFC.canceledInvoicesWithoutIssued : 0);

      arrayAnuladaConNumeroFiscal.push('canceledInvoicesWithTaxNumber' in RESULT_MAP[type]? valueFC.canceledInvoicesWithTaxNumber : 0);

      arrayError.push('errorInvoice' in RESULT_MAP[type]? valueFC.errorInvoice : 0);



    });

   
    // Construyendo gráficas
    this.elementGraphType = new Chart(this.elementGraph, {

      type: 'bar',

      data: {
        labels: Object.keys(RESULT_MAP),
        datasets: [
          {
            label: "Pendientes",
            backgroundColor: this.getRandomColor(),
            data: [arrayPendiente[0],arrayPendiente[1],arrayPendiente[2],arrayPendiente[3],arrayPendiente[5],arrayPendiente[6],arrayPendiente[7]]
          },
          {
            label: "Autorizadas",
            backgroundColor: this.getRandomColor(),
            data: [arrayAutorizada[0],arrayAutorizada[1],arrayAutorizada[2],arrayAutorizada[3],arrayAutorizada[5],arrayAutorizada[6],arrayAutorizada[7]]
          },
          {
            label: "Emitidas",
            backgroundColor: this.getRandomColor(),
            data: [arrayEmitida[0],arrayEmitida[1],arrayEmitida[2],arrayEmitida[3],arrayEmitida[5],arrayEmitida[6],arrayEmitida[7]]
          },
          {
            label: "Anulada Sin Emitir",
            backgroundColor: this.getRandomColor(),
            data: [arrayAnuladaSinEmitir[0],arrayAnuladaSinEmitir[1],arrayAnuladaSinEmitir[2],arrayAnuladaSinEmitir[3],arrayAnuladaSinEmitir[5],arrayAnuladaSinEmitir[6],arrayAnuladaSinEmitir[7]]
          },
          {
            label: "Anulada Con Número Fiscal",
            backgroundColor: this.getRandomColor(),
            data: [arrayAnuladaConNumeroFiscal[0],arrayAnuladaConNumeroFiscal[1],arrayAnuladaConNumeroFiscal[2],arrayAnuladaConNumeroFiscal[3],arrayAnuladaConNumeroFiscal[5],arrayAnuladaConNumeroFiscal[6],arrayAnuladaConNumeroFiscal[7]]
          },
          {
            label: "Error",
            backgroundColor: this.getRandomColor(),
            data: [arrayError[0],arrayError[1],arrayError[2],arrayError[3],arrayError[5],arrayError[6],arrayError[7]]
          },
        ]
      },
      options: {

      },


    });

    this.elementGraphType.update();


  }

  getRandomColor() {
    return `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.7)`;
  }





}
