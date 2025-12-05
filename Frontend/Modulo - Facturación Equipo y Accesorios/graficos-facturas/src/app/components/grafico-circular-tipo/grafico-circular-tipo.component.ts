import { Component, Input, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { InvoiceStatistics } from 'src/app/model/model';

@Component({
  selector: 'app-grafico-circular-tipo',
  templateUrl: './grafico-circular-tipo.component.html',
  styleUrls: ['./grafico-circular-tipo.component.css']
})
export class GraficoCircularTipoComponent implements OnInit {

  // Props
  @Input() inputData: InvoiceStatistics;
  colors: string[] = [];
  total:any = 0;
  suma: number = 0;
  arrayData:any = 0;

  // Elements HTML
  elementGraph: any = null;

  // Elemento CANVAS
  elementGraphType: any = null;

  constructor() { }

  ngOnInit(): void {
    this.addColor();
    this.buildGraphics();
  }

  /**
 * Método para obtener colores randon para la grafica
 * 
 */
  addColor() {
    for (let i = 0; i <= 20; i++) {
      this.colors.push(this.getRandomColor());
    }
  }

  /**
   * 
   */
  getRandomColor() {
    return `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.7)`;
  }

  /**
 * Método encargado de crear las gráficas
 * 
 */
  async buildGraphics() {


    // Obtenemos los elementos por el id 
    this.elementGraph = document.getElementById('graf_type') as HTMLCanvasElement;

    this.buildGraphicsCircleAll();

  }

  /**
* Método encargado de construir gráficos circulares
* 
*/
  buildGraphicsCircleAll(): void {


    // Construyendo gráficas
    this.elementGraphType = new Chart(this.elementGraph, {

      type: 'pie',

      data: {
        labels: Object.keys(this.inputData.invoicesByType),

        datasets: [{
          label: "",
          backgroundColor: this.colors,
          data: Object.values(this.inputData.invoicesByType)
        }]
      },
      options: {
        tooltips: {
          callbacks: {
            label: function (tooltipItem, data) {

             let arrayData = data.datasets[0]['data'];
             let suma = 0;

             for(let i =0; i<=arrayData.length-1;i++){
              
               suma += Number(arrayData[i]);
              }
              

             /*
              let data1 = Number(data.datasets[0]['data'][0]);
              let data2 = Number(data.datasets[0]['data'][1]);
              let data3 = Number(data.datasets[0]['data'][2]);
              let data4 = Number(data.datasets[0]['data'][3]);
              let suma = data1 + data2 + data3 + data4 ;
              */
              let actual = Number(data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index]);
              this.total = actual;
              let porcentaje = Number((actual / suma) * 100).toFixed(2) + '%';
              return actual + ' de ' + suma + ' : ' + porcentaje;
            },
          }
        },

        maintainAspectRatio: false,

        title: {
          display: true,
          text: `Total Tipos de Facturas`
        }

      }

    });

    this.elementGraphType.update();


  }



}
