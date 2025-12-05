import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PromotionModalComponent } from 'src/app/components/promotion-modal/promotion-modal.component';
import { PromotionResponse } from 'src/app/entity/response';
import { PromotionModel } from 'src/app/model/model';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/emun';

@Component({
  selector: 'app-promotion-comodatos',
  templateUrl: './promotion-comodatos.component.html',
  styleUrls: ['./promotion-comodatos.component.css']
})
export class PromotionComodatosComponent implements OnInit {

  // Props

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 5;
  searchedValue: string = "";
  rows: PromotionModel[] = [];
  rows2: PromotionModel[] = [];


  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-3";

  constructor(public utilService: UtilService, private modalService: NgbModal, private comodatosService: ComodatosService, private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.getPromotions();
  }

  // Methods

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

  getStatusColorClass(statusColor: string | undefined): string {
    switch (statusColor) {
      case 'Vigente':
        return 'green-text';
      default:
        return 'red-text';
    }
  }

  openModal(action: string, data?: PromotionModel) {

    const modalRef = this.modalService.open(PromotionModalComponent, {
      size: "xl", backdrop: "static"
    })

    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;

    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {
      if (status === true) {
        this.getPromotions();
      }
    });

  }

  /**
   * Valida si un registro no es permanente, entonces,
   * la fecha fin se compara con la fecha actual
   * 
   */
  validateDate(dateEnd: Date): Promise<boolean> {
    return new Promise((resolve, reject) => {

      if (dateEnd) {

        // Valor Actual
        const dateCurrent: Date = new Date();
        const dateCurrentStr: string = this.datePipe.transform(dateCurrent, "yyyy-MM-dd");
        const dateCurrentValidate: Date = new Date(dateCurrentStr);

        // Valor Final
        const dateEndStr: string = this.datePipe.transform(dateEnd, "yyyy-MM-dd");
        const dateEndValidate: Date = new Date(dateEndStr);

        if (dateCurrentValidate > dateEndValidate) {
          
          resolve(false);
          } else {
            
            resolve(true);
        }

      } else {
        resolve(false);
      }

    });
  }

  // Methods Rest

  /**
   * Método que consume un servicio para obtener las promociones
   * 
   */
  getPromotions() {

    // Se llama e método del servicio
    this.comodatosService.getPromotions().subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let promotionResponse = response.body as PromotionResponse;

        // Agregamos los valores a los rows

        promotionResponse.data.map(async (resourceMap, configError) => {

          let dto: PromotionModel = resourceMap;

          /**
           * Si es permanente siempre será vigente, sino se comparan la fecha fin con la fecha actual
           * 
           */
          if (dto.permanentValidity === "Y") {
            dto.statusName = "Vigente";
          } else {

            if(dto.endDate){
              const VALIDATE_DATE = await this.validateDate(dto.endDate);

              dto.statusName = VALIDATE_DATE===true ? "Vigente" : "Vencido";

            }

          }
          this.rows.push(dto);

        });

        this.loadingIndicator = false;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    }, (error) => {

    })
  }


}
