import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DataResponse } from 'src/app/entity/response';
import { DataModel, OrderControlModel, OrderDetailModel } from 'src/app/model/model';
import { SelectComponentComponent } from 'src/app/plugin/select-component/select-component.component';
import { SimcardService } from 'src/app/service/simcard.service';
import { UtilService } from 'src/app/service/util.service';

@Component({
  selector: 'app-consult-simcard',
  templateUrl: './consult-simcard.component.html',
  styleUrls: ['./consult-simcard.component.css']
})
export class ConsultSimcardComponent implements OnInit {

  // Props

  // PARAMS
  param: any = 0;
  type: any = 0;

  // Style
  inputClasses = "my-auto col-sm-12 col-md-3 col-lg-3";

  // Table
  rows: DataModel[] = [];
  rows2: DataModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  mostrarTabla = false;
  infoCargado = false;

  // Select
  public dropsownSettings = {};
  public dropDownList = [
    { id: 1, itemName: "IMSI" }, { id: 2, itemName: "ICC" }
  ];

  public selectedItem = [];
  private sub: any;


  constructor(public utilService: UtilService, private modalService: NgbModal, private activatedRoute: ActivatedRoute,
    private router: Router, private simcardService: SimcardService) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.sub = JSON.stringify(params['sub']);

      if (this.sub)
        sessionStorage.setItem('sub', this.sub)
    });
  }

  ngOnInit(): void {
    this.dropsownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };
    this.dropDownList = [
      { id: 1, itemName: "IMSI" }, { id: 2, itemName: "ICC" }
    ];
    this.selectedItem = [];
    let permisos = this.loadPermisos();
    this.activatedRoute.params.subscribe(params => {
      this.param = params['param'];
      this.type = params['type'];
      if (this.type === '1') {
        this.selectedItem = [this.dropDownList[0]];
      } else if (this.type === '2') {
        this.selectedItem = [this.dropDownList[1]];
      }
      if (this.param != 0 && this.param != undefined && this.param != "undefined") {
        if (permisos.filter(x => x == 'GB').length > 0) {
          if (this.type == '1' || this.type == '2') {
            this.getImsiOrIccd(this.param, this.type);
          } else {
            this.mostrarTabla = false;
            this.infoCargado = false;
            let caja = document.getElementsByClassName("c-btn")[0];
            caja.setAttribute('style', 'background-color: #fdd7d7;');
            setTimeout(function () { caja.setAttribute('style', ''); }, 3000);
            this.utilService.setMsg('¡Debe seleccionar un parametro de búsqueda!', '', 2000, 'info', 'toast-top-right');
          }
        } else {
          this.utilService.setMsg('¡No tiene permisos para realizar esta consulta!', '', 2000, 'danger', 'toast-top-right');
        }
      }

    })

  }

  loadPermisos() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permisos = subs[2]
    return permisos.split('_')
  }


  // Methods

  /**
   * Método que nos ayuda a validar si se busca por un valor en la barra de búsqueda de Nexus y si 
   * selecciono el tipo de consulta, si no hay valor de busqueda ni del tipo de consulta automaticamente 
   * carga todos los pedidos.
   * 
   */
  /*  async loadData() {
 
 
     this.route.params.subscribe( async (params)=>{
       this.param = params['param'];
       this.type = params['type'];
 
 
 
       if (this.param != 0 && this.param != undefined && this.param != "undefined") {
 
         if (this.type == 1) {
           this.selectedItem = [this.dropDownList[0]];
           const VALIDATE_FOUND = await this.getImsiOrIccd(this.param, this.type);
   
           if(VALIDATE_FOUND==false){
             this.utilService.showNotification(1, `No se encontro el registro ${this.param}`);
           }
   
         } else if (this.type == 2) {
           this.selectedItem = [this.dropDownList[1]];
           const VALIDATE_FOUND = await this.getImsiOrIccd(this.param, this.type);
   
           if(VALIDATE_FOUND==false){
             this.utilService.showNotification(1, `No se encontro el registro ${this.param}`);
           }
   
         } else {
           this.utilService.showNotification(1, "¡Debe seleccionar un tipo de consulta!");
         }
   
       } else {
   
         if(this.type != 0 && this.type != undefined && this.type != "undefined"){
   
           if(this.type==1){
             this.selectedItem = [this.dropDownList[0]];
           }else{
             this.selectedItem = [this.dropDownList[1]];
           }
   
           this.utilService.showNotification(1, "¡Debe de ingresar un valor en la barra de búsqueda de Nexus!");
         }else{
           
         }
   
         
       }
       
     })
 
 
 
   }  */

  // Generales
  search() {
    this.rows = this.rows2.filter((rowsSimcardControl) => {
      return JSON.stringify(rowsSimcardControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  reloadRows() {
    //this.getOrdersControl();
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  onItemSelect(ev) {
    this.infoCargado = false;
    this.mostrarTabla = false;
    this.type = this.selectedItem[0]['id'];
    this.router.navigateByUrl('/mov' + '/0/' + this.type)
      .finally(() => { this.reFresh() })
  }

  OnItemDeSelect(ev) {
    this.router.navigateByUrl('/mov' + '/' + this.param + '/0')
      .finally(() => { this.reFresh() })
  }

  public reFresh() {
    let url = this.type
    parent.postMessage(url, "*");
  }




  // Modal
  openModal(data: DataModel) {


    // Se abre la modal
    const modalRef = this.modalService.open(SelectComponentComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.orderControl = data.orderControl;
    modalRef.componentInstance.orderDetail = data.details[0];


  }

  getStatusColorClass(statusColor: string | undefined): string {
    switch (statusColor) {
      case 'E':
        return 'blue-text';
      case 'R':
        return 'green-text';
      default:
        return 'red-text';
    }
  }


  /**
   * Método que consume un servicio para traer la información ya sea por el imsi
   * ó iccd
   * 
   * @param value 
   * @param type 
   * @returns 
   */
  getImsiOrIccd(value: any, type: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.getImsiOrIccd(value, type).subscribe((response) => {

        if (response.status === 200) {
          this.rows = [];
          this.rows2 = [];

          const dataResponse = response.body as DataResponse;
          dataResponse.data.orderControl.statusCode = dataResponse.data.orderControl.status === null ? 'Pendiente' : dataResponse.data.orderControl.status === "E" ? "Procesar" : "Procesado";


          this.rows.push(dataResponse.data);

          this.rows = [...this.rows];
          this.rows2 = [...this.rows];

          if (this.rows.length > 0) {
            this.utilService.showNotification(0, "Registro encontrado");
          } 

          resolve(true);

        } else {
          this.rows = [];
          this.rows2 = [];
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];
          resolve(false);
        }

      }, (error) => {

        if (error.status === 400) {
          this.utilService.showNotification(1, `No se encontró el registro ${this.param}`);
        } else {
          this.utilService.setMsg('Ocurrió un error al realizar la búsqueda.', '', 2000, 'danger', 'toast-top-right');
        }
        this.rows = [];
        this.rows2 = [];
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        resolve(false);
      });

    });
  }


}
