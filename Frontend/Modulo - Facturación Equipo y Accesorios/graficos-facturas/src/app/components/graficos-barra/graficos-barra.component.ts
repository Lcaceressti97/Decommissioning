import { Component, Input, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { InvoiceStatistics } from 'src/app/model/model';

@Component({
  selector: 'app-graficos-barra',
  templateUrl: './graficos-barra.component.html',
  styleUrls: ['./graficos-barra.component.css']
})
export class GraficosBarraComponent implements OnInit {

  // Props

  // Inputs
  @Input() inputData: InvoiceStatistics;
  colors: string[] = [];

  // Elements HTML
  elementGraph: any = null;

  // Elemento CANVAS
  elementGraphSeller: any = null;

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
* Método encargado de crear las gráficas
* 
*/
  async buildGraphics() {


    // Obtenemos los elementos por el id 
    this.elementGraph = document.getElementById('graf_seller') as HTMLCanvasElement;

    this.buildGraphicsCircleAll();

  }

  /**
* Método encargado de construir gráficos circulares
* 
*/
  buildGraphicsCircleAll(): void {



    // Construyendo gráficas
    this.elementGraphSeller = new Chart(this.elementGraph, {

      type: 'bar',

      data: {

        // Parte inferior de las barras
        labels: Object.keys(this.inputData.invoicesPerSeller),


        datasets: [{
          label: "",
          backgroundColor: this.colors,
          data: Object.values(this.inputData.invoicesPerSeller)
        }]
      },
      options: {
        legend: {
          display: false
        },
        title: {
          display: true,
          text: "Facturas Por Vendedor"
        }
      },


    });

    this.elementGraphSeller.update();


  }

  getRandomColor() {
    return `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.7)`;
  }

}
