import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MooringBillingResponse, ParametersResponse } from 'src/app/entity/response';
import { MooringBillingModel, ParametersModel } from 'src/app/model/model';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';
import { ConsultaComodatosDetailComponent } from 'src/app/view/consulta-comodatos-detail/consulta-comodatos-detail.component';

@Component({
  selector: 'app-consulta-comodatos-suscriptor',
  templateUrl: './consulta-comodatos-suscriptor.component.html',
  styleUrls: ['./consulta-comodatos-suscriptor.component.css']
})
export class ConsultaComodatosSuscriptorComponent implements OnInit {


  // Props

  // Params
  parametersMap = new Map<string, string>();

  param: any = 0;
  type: any = 0;

  // Select
  public dropsownSettings = {};
  public dropDownList = [
    { id: 1, itemName: "Suscriptor" }, { id: 5, itemName: "Correlativo Comodato" },
    { id: 6, itemName: "Correlativo Amarre" }
  ];

  public selectedItem = [];
  private sub: any;


  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: MooringBillingModel[] = [];
  rows2: MooringBillingModel[] = [];
  size: number = 2000;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-3";

  constructor(public utilService: UtilService, private comodatosService: ComodatosService, private route: ActivatedRoute, private modalService: NgbModal, private router: Router) {
    this.route.queryParams.subscribe(params => {
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
      { id: 1, itemName: "Suscriptor" }, { id: 5, itemName: "Correlativo Comodato" },
      { id: 6, itemName: "Correlativo Amarre" }
    ];

    this.selectedItem = [];

    let permisos = this.loadPermisos();

    this.route.params.subscribe(async (params) => {
      this.param = params['param'];
      this.type = params['type'];

      if (this.type === '1') {
        this.selectedItem = [this.dropDownList[0]];
      } else if (this.type === '5') {
        this.selectedItem = [this.dropDownList[1]];
      }else if (this.type === '6'){
        this.selectedItem = [this.dropDownList[2]];
      }

      if (this.param != 0 && this.param != undefined && this.param != "undefined") {
        if (permisos.filter(x => x == 'GB').length > 0) {
          if (this.type == '1') {
            const VALIDATE_FOUND = await this.getByFilter(this.type, this.param);

            if(VALIDATE_FOUND==false){
              this.utilService.showNotification(1, `No se encontro el registro ${this.param}`);
              this.rows = [];
              this.rows2 = [];
              this.rows = [...this.rows];
              this.rows2 = [...this.rows];
            }
            
            
          } else if(this.type == '5' || this.type == '6'){
              const VALIDATE_FOUND = await this.getComodatosByConsult( this.type,this.param);

              if(VALIDATE_FOUND==false){
                this.utilService.showNotification(1, `No se encontro el registro ${this.param}`);
                this.rows = [];
                this.rows2 = [];
                this.rows = [...this.rows];
                this.rows2 = [...this.rows];
              }

          } else {
            //this.mostrarTabla = false;
            //this.infoCargado = false;
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

    //this.loadData();

  }

  loadPermisos() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permisos = subs[2]
    return permisos.split('_')
  }

  /**
   * Método que nos ayuda a validar si se busca por un valor en la barra de búsqueda de Nexus y si 
   * selecciono el tipo de consulta, si no hay valor de busqueda ni del tipo de consulta automaticamente 
   * carga todos los pedidos.
   * 
   */
  async loadData() {

    this.param = this.route.snapshot.paramMap.get('param');
    this.type = this.route.snapshot.paramMap.get('type');

    if (this.param != 0 && this.param != undefined && this.param != "undefined") {

      if (this.type == 1) {
        this.selectedItem = [this.dropDownList[0]];

        
        const VALIDATE_FOUND = await this.getByFilter(this.type, this.param);

        if(VALIDATE_FOUND==false){
          this.utilService.showNotification(1, `No se encontro el registro ${this.param}`);
        }
        

      } else if (this.type == 5) {
        this.selectedItem = [this.dropDownList[1]];
        
        const VALIDATE_FOUND = await this.getComodatosByConsult( this.type,this.param);

        if(VALIDATE_FOUND==false){
          this.utilService.showNotification(1, `No se encontro el registro ${this.param}`);
        }
        

      } else if (this.type == 6) {
        this.selectedItem = [this.dropDownList[2]];
        const VALIDATE_FOUND = await this.getComodatosByConsult( this.type,this.param);

        if(VALIDATE_FOUND==false){
          this.utilService.showNotification(1, `No se encontro el registro ${this.param}`);
        }

      } else {
        this.utilService.showNotification(1, "¡Debe seleccionar un tipo de consulta!");
      }

    } else {

      if (this.type != 0 && this.type != undefined && this.type != "undefined") {

        if (this.type == 1) {
          this.selectedItem = [this.dropDownList[0]];
        } else if (this.type == 5) {
          this.selectedItem = [this.dropDownList[1]];
        } else {
          this.selectedItem = [this.dropDownList[2]];
        }

        this.utilService.showNotification(1, "¡Debe de ingresar un valor en la barra de búsqueda de Nexus!");
      } else {

      }


    }

  }

  onItemSelectTest(ev) {
    this.type = this.selectedItem[0]['id'];
    this.router.navigateByUrl('/mov' + '/0/' + this.type)
      .finally(() => { this.reFresh() })
  }

  onItemSelect(event: any) {
    //this.type = event.id;
    //this.router.navigateByUrl('mov' + '/' + this.param + '/' + this.type);

    this.type = this.selectedItem[0]['id'];
    //console.log(this.type)
    this.router.navigateByUrl('/mov/' + this.param + '/' + this.type)
      .finally(() => { this.reFresh() })
  }

  OnItemDeSelectTest(ev) {
    this.router.navigateByUrl('/mov' + '/' + this.param + '/0')
      .finally(() => { this.reFresh() })
  }

  onItemDeSelect(event: any) {
    //this.type = 0;
    //this.router.navigateByUrl('mov' + '/' + this.param + '/' + this.type);
    this.rows = [];
    this.rows2 = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];

    this.router.navigateByUrl('/mov' + '/' + this.param + '/0')
    .finally(() => { this.reFresh() })
  }

  
  public reFresh() {
    let url = this.type
    parent.postMessage(url, "*");
  }


  search() {
    this.rows = this.rows2.filter((approvalControl) => {
      return JSON.stringify(approvalControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  openModal(data?: any, option?: string) {

    const modalRef = this.modalService.open(ConsultaComodatosDetailComponent, {
      size: "lg", backdrop: "static"
    })

    modalRef.componentInstance.data = data;
    modalRef.componentInstance.parameter = this.parametersMap;

  }


  // Metodos Asyncronos

  /**
* Método encargado de obtener los parámetros de la pantalla
* 
*/
  configparametersById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.comodatosService.configparametersById(id).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {


          // Mapeamos el body del response
          let configParameterResponse = response.body as ParametersResponse;

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: ParametersModel = resourceMap;


            this.parametersMap.set(dto.parameterName, dto.value.toString());




          });

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })
    });



  }

  /**
   * Método encargado de obtener los valores según el filtro que el usuario selecciono
   * 
   */
  getByFilter(type, value): Promise<boolean> {
    return new Promise((resolve,reject)=>{
      this.comodatosService.getComodatosByFilter(value, type).subscribe((res) => {
        if (res.status === 200) {
          this.loadingIndicator = false;
          let mooringBillingResponse = res.body as MooringBillingResponse;
  
          if (mooringBillingResponse.data.length > 0) {
  
            mooringBillingResponse.data.map((resourceMap, configErro) => {
              let dto: MooringBillingModel = resourceMap;
              dto.mooring = dto.mooring === 1 ? "Sí" : "No";
              dto.cmdStatus = dto.cmdStatus === "A" ? "Activo" : dto.cmdStatus === "V" ? "Vencido" : dto.cmdStatus === "C" ? "Cancelado" : "No tiene";
  
              this.rows.push(dto);
            });
  
            this.loadingIndicator = false;
            this.rows = [...this.rows];
            this.rows2 = [...this.rows];
  
            if (this.rows.length > 0) {
              this.utilService.showNotification(0, "Datos encontrados");
            } else {
              this.utilService.showNotification(1, `No se encontraron datos con el valor ${value}`);
            }
            resolve(true);
  
          } else {
            //this.utilService.showNotification(1, `No se encontraron datos con el valor ${value}`);
            resolve(false)
          }
  
        } else {
          this.utilService.showNotificationError(res.status);
        }
      }, (error) => {
        resolve(false);
      });
    })

  }

  getComodatosByConsult(type, value): Promise<boolean> {
    return new Promise((resolve,reject)=>{
      this.comodatosService.getComodatosByConsult(value, type).subscribe((res) => {
        if (res.status === 200) {
          this.loadingIndicator = false;
          let mooringBillingResponse = res.body as MooringBillingResponse;
  
          if (mooringBillingResponse.data.length > 0) {
  
            mooringBillingResponse.data.map((resourceMap, configErro) => {
              let dto: MooringBillingModel = resourceMap;
              dto.mooring = dto.mooring === 1 ? "Sí" : "No";
              dto.cmdStatus = dto.cmdStatus === "A" ? "Activo" : dto.cmdStatus === "V" ? "Vencido" : dto.cmdStatus === "C" ? "Cancelado" : "No tiene";
  
              this.rows.push(dto);
            });
  
            this.loadingIndicator = false;
            this.rows = [...this.rows];
            this.rows2 = [...this.rows];
  
            if (this.rows.length > 0) {
              this.utilService.showNotification(0, "Datos encontrados");
            } else {
              this.utilService.showNotification(1, `No se encontraron datos con el valor ${value}`);
            }
            resolve(true);
  
          } else {
            //this.utilService.showNotification(1, `No se encontraron datos con el valor ${value}`);
            resolve(false)
          }
  
        } else {
          this.utilService.showNotificationError(res.status);
        }
      }, (error) => {
        resolve(false);
      });
    })

  }


}
