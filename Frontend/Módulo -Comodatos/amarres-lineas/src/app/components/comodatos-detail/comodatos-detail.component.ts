import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MooringBillingModel } from 'src/app/model/model';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-comodatos-detail',
  templateUrl: './comodatos-detail.component.html',
  styleUrls: ['./comodatos-detail.component.css']
})
export class ComodatosDetailComponent implements OnInit {

  // Props
  // Input y Output
  @Input() data: MooringBillingModel;
  @Input() parameter: Map<string, string>;
  readonlyInput: boolean = true;

  // Tab
  active: number = 1;
  titleCard: string = "Datos Generales";
  card1: boolean =true;
  card2: boolean =true;
  card3: boolean =true;
  card4: boolean =true;
  card5: boolean =true;

  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private comodatosService: ComodatosService,) { }

  ngOnInit(): void {
    //console.log(this.parameter);
    if(!this.data.vac){
      this.data.vac=0;
    }
  }

  closeModal(){
    this.activeModal.close();
  }

  changeTab(option:number){

    this.active = option;

    this.titleCard = this.active===1 ? "Datos Generales" : this.active===2 ? "Datos de Cuentas" : this.active===3 ? "Datos Facturas" : this.active===4 ? "Datos del Producto" : "Datos Usuarios";

  }

}
