import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MooringBillingModel } from 'src/app/model/model';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-consulta-comodatos-detail',
  templateUrl: './consulta-comodatos-detail.component.html',
  styleUrls: ['./consulta-comodatos-detail.component.css']
})
export class ConsultaComodatosDetailComponent implements OnInit {

  // Props
  // Input y Output
  @Input() data: MooringBillingModel;

  readonlyInput: boolean = true;

  // Tab
  active: number = 1;

  // Dates
  fechaIngreso: string = "";
  fechaCancelacion: string = "";
  fechaEstimada: string = "";
  mesesPendientes: any = "0";

  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private comodatosService: ComodatosService, private datePipe: DatePipe) { }

  ngOnInit(): void {

    this.getDates();


  }

  closeModal() {
    this.activeModal.close();
  }

  changeTab(option: number) {
    this.active = option;

  }

  /**
   * Método encargado de calcular las fechas
   * 
   */
  getDates(): void {


    this.fechaIngreso = this.datePipe.transform(this.data.createDate, "dd/MM/yyyy");
    this.fechaCancelacion = this.datePipe.transform(this.data.dueDate, "dd/MM/yyyy");
    let dateCancel: Date = null;

    // Calculando la fecha estimada
    if (this.data.dueDate) {
      dateCancel = new Date(this.data.dueDate);
    }

    let fecha: Date = new Date(this.data.createDate);
    fecha.setMonth(fecha.getMonth() + this.data.monthsOfPermanence);



    let fechaEstimda: Date = new Date(fecha);
    this.fechaEstimada = this.datePipe.transform(fechaEstimda, "dd/MM/yyyy");

    if (this.data.dueDate) {
      this.mesesPendientes = fechaEstimda.getMonth() - dateCancel.getMonth();
    }else{
      this.mesesPendientes = this.data.monthsOfPermanence;
    }


    if (this.mesesPendientes <= 0) {
      this.mesesPendientes = 0;
    }



    if (!this.data.vac) {
      this.data.vac = 0;
    }


  }

}
