import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ComodatosCancelationComponent } from 'src/app/components/comodatos-cancelation/comodatos-cancelation.component';
import { ComodatosDetailComponent } from 'src/app/components/comodatos-detail/comodatos-detail.component';
import { ComodatosMooringComponent } from 'src/app/components/comodatos-mooring/comodatos-mooring.component';
import { LineMooriningDetailComponent } from 'src/app/components/line-moorining-detail/line-moorining-detail.component';
import { MooringBillingModel, ParametersModel } from 'src/app/model/model';
import { MooringBillingPaginationResponse, MooringBillingResponse, ParametersResponse } from 'src/app/rentity/response';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-line-mooring',
  templateUrl: './line-mooring.component.html',
  styleUrls: ['./line-mooring.component.css']
})
export class LineMooringComponent implements OnInit {

  // Props

  // Params
  parametersInvoiceStatus: ParametersModel[] = [];
  parameters: ParametersModel[] = [];
  // Crear un Map a partir del JSON
  parametersMap = new Map<string, string>();
  parametersMap4462 = new Map<string, string>();

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

  type: string = '0';
  param: any;
  private sub: any;
  private isInitialLoad = true;

  // Select
  public dropdownSettings = {};
  public dropdownList = [
    {
      id: 1,
      itemName: "Suscriptor"
    },
    {
      id: 2,
      itemName: "Cuenta del Cliente"
    },
    {
      id: 3,
      itemName: "Cuenta de Servicio"
    },
    {
      id: 4,
      itemName: "Cuenta Facturación"
    }
  ];
  public selectedItems = [];

  constructor(public utilService: UtilService, private modalService: NgbModal, private activatedRoute: ActivatedRoute,
    private router: Router, private comodatosService: ComodatosService) {
      this.activatedRoute.queryParams.subscribe(params => {
        this.sub = JSON.stringify(params['sub']);
  
        if (this.sub)
          sessionStorage.setItem('sub', this.sub)
      });

  }

  async ngOnInit() {
    
    this.dropdownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

     this.dropdownList = [
      {
        id: 1,
        itemName: "Suscriptor"
      },
      {
        id: 2,
        itemName: "Cuenta del Cliente"
      },
      {
        id: 3,
        itemName: "Cuenta de Servicio"
      },
      {
        id: 4,
        itemName: "Cuenta Facturación"
      }
    ];

    this.selectedItems = [];
    let permisos = this.loadPermisos();

    await this.configparametersById(1000);
    await this.configparametersById(4462);

    this.activatedRoute.params.subscribe(params => {

      this.param = params['param'];
      this.type = params['type'];

      if (this.type === '1') {
        this.selectedItems = [this.dropdownList[0]];
      } else if (this.type === '2') {
        this.selectedItems = [this.dropdownList[1]];
      }else if(this.type === '3'){
        this.selectedItems = [this.dropdownList[2]];
      }else if(this.type === '4'){
        this.selectedItems = [this.dropdownList[3]];
      }


      if (this.param != 0 && this.param != undefined && this.param != "undefined") {
        if (permisos.filter(x => x == 'GB').length > 0) {
          if (this.type == '1' || this.type == '2' || this.type == '3' || this.type == '4') {
            this.getBy(this.param, this.type);
          } else {
            //this.mostrarTabla = false;
            //this.infoCargado = false;
            let caja = document.getElementsByClassName("c-btn")[0];
            caja.setAttribute('style', 'background-color: #fdd7d7;');
            setTimeout(function () { caja.setAttribute('style', ''); }, 3000);
            this.utilService.setMsg('¡Debe seleccionar un parametro de búsqueda!', '', 2000, 'info', 'toast-top-right');
            this.rows = [];
            this.rows2 = [];
            this.rows = [...this.rows];
            this.rows2 = [...this.rows];
            this.getAllInvoicePagination(0, this.size, 1);
          }
        } else {
          this.utilService.setMsg('¡No tiene permisos para realizar esta consulta!', '', 2000, 'danger', 'toast-top-right');
        }
      } else {
        if (this.isInitialLoad) {
          this.isInitialLoad = false;
          this.getAllInvoicePagination(0, this.size, 1);
        }
      }
    })


  }

  // Métodos de la tabla
  search() {
    this.rows = this.rows2.filter((approvalControl) => {
      return JSON.stringify(approvalControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  loadPermisos() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permisos = subs[2]
    return permisos.split('_')
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  async reloadRows() {
    // await this.configparametersById(1000);
    this.getAllInvoicePagination(0, this.size);
  }

  onItemSelect(ev) {
    //this.type = this.selectedItems[0]['id'];
    //console.log(this.type)
    //this.router.navigateByUrl('mov/' + this.param + '/' + this.type);

    this.type = this.selectedItems[0]['id'];
    //console.log(this.type)
    this.router.navigateByUrl('/mov/' + this.param + '/' + this.type)
      .finally(() => { this.reFresh() })
  }

  OnItemDeSelect(ev) {
    //this.router.navigateByUrl('mov' + '/'+ this.param +'/0' );
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

  /**
   * No está en uso este metodo
   * 
   */
  noUsed(){
        /*
    this.param = this.activatedRoute.snapshot.paramMap.get('param');
    this.type = this.activatedRoute.snapshot.paramMap.get('type');

    if (this.type === '1') {
      this.selectedItems = [this.dropdownList[0]];
    } else if (this.type === '2') {
      this.selectedItems = [this.dropdownList[1]];
    } else if (this.type === '3') {
      this.selectedItems = [this.dropdownList[2]];
    } else if (this.type === '4') {
      this.selectedItems = [this.dropdownList[3]];
    }

  
    if (this.param != 0 && this.param != undefined && this.param != "undefined") {

      if (this.type == '1' || this.type == '2' || this.type == '3' || this.type == '4') {

        this.getBy(this.param, this.type);

      } else {

        this.utilService.setMsg('¡Debe seleccionar el tipo de consulta!', '', 2000, 'info', 'toast-top-right');

      }

    } else {

      if (this.param == 0 && this.type == '0') {
        this.getAllInvoicePagination(0, this.size, 1);
      }else{
        this.utilService.showNotification(1, "Debe de ingresar un valor en la barra de búsqueda de Nexus");
      }
    }
    */
  }


  openModal(data?: any, option?: string) {

    const modalRef = this.modalService.open(ComodatosDetailComponent, {
      size: "xl", backdrop: "static"
    })

    modalRef.componentInstance.data = data;
    modalRef.componentInstance.parameter = this.parametersMap;

  }

  openModalMooring(data?: any) {

    const modalRef = this.modalService.open(ComodatosMooringComponent, {
      size: "xl", backdrop: "static"
    })

    modalRef.componentInstance.data = data;
    modalRef.componentInstance.parameter = this.parametersMap;
    modalRef.componentInstance.parameter4462 = this.parametersMap4462;

    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {
      if (status === true) {
        this.getAllInvoicePagination(0, this.size, 2);
      }
    });

  }

  openModalCancelation(data?: any) {

    const modalRef = this.modalService.open(ComodatosCancelationComponent, {
      size: "xl", backdrop: "static"
    })

    modalRef.componentInstance.data = data;
    modalRef.componentInstance.parameter = this.parametersMap;
    modalRef.componentInstance.parametersMap4462 = this.parametersMap4462;

    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {
      if (status === true) {
        this.utilService.showNotification(0, "Registro cancelado exitosamente");
        this.reloadRows();
      }
    });

  }

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

            if (id === 1000) {
              //this.parametersInvoiceStatus.push(dto);
              this.parametersMap.set(dto.parameterName, dto.value.toString());
            } else {
              //this.parameters.push(dto);
            }

            if(id===4462){
              this.parametersMap4462.set(dto.parameterName, dto.value.toString());
            }


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
   * Método encargado de obtener los registros de las facturas
   * 
   */
  getAllInvoicePagination(page: any, size: any, optional?: number) {

    // Se llama e método del servicio
    this.comodatosService.getComodatosAllPagination(page, size).subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let billingResponse = response.body as MooringBillingPaginationResponse;

        // Agregamos los valores a los rows

        billingResponse.content.map((resourceMap, configError) => {

          let dto: MooringBillingModel = resourceMap;
          dto.mooring = dto.mooring === 1 ? "Sí" : "No";
          dto.cmdStatus = dto.cmdStatus === "A" ? "Activo" : dto.cmdStatus === "V" ? "Vencido" : dto.cmdStatus === "C" ? "Cancelado" : "No tiene";

          this.rows.push(dto);

        });

        this.loadingIndicator = false;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];

        if (this.rows.length > 0) {
          if (optional === 1) {
            this.utilService.showNotification(0, "Datos cargados");
          }
        }

      }

    }, (error) => {

    })
  }


  /**
   * Método encargado de obtener los valores según el filtro que el usuario selecciono
   * 
   */
  getBy(type, value) {
    this.comodatosService.getComodatosByFilter(type, value).subscribe((res) => {
      this.rows = [];
      this.rows2 = [];
      this.rows = [...this.rows];
      this.rows2 = [...this.rows];
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

          this.utilService.showNotification(0, "Datos encontrados");

        } else {
          this.utilService.showNotification(1, `No se encontraron datos con el valor ${type}`);
        }

      } else {
        this.utilService.showNotificationError(res.status);
      }
    }, (error) => {

    });
  }


}
