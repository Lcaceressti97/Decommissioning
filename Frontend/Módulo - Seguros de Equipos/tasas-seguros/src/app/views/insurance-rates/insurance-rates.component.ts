import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InsuranceRatesModalComponent } from 'src/app/components/insurance-rates-modal/insurance-rates-modal.component';
import { InsuranceRatesResponse } from 'src/app/entities/response';
import { InsuranceRatesModel } from 'src/app/models/model';
import { InsuranceRatesService } from 'src/app/services/insurance-rates.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';

@Component({
  selector: 'app-insurance-rates',
  templateUrl: './insurance-rates.component.html',
  styleUrls: ['./insurance-rates.component.css']
})
export class InsuranceRatesComponent implements OnInit {


  // Props

  // Table
  messages = messages;
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: InsuranceRatesModel[] = [];
  rows2: InsuranceRatesModel[] = [];

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-8";
  inputClassesTwo = "my-auto col-sm-12 col-md-2 col-lg-2";

  constructor(public utilService: UtilService, private insuranceRatesService: InsuranceRatesService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getInsuranceRates();
  }

  // Methods

  /**
  * Nos ayuda a filtrar, es decir: nos ayuda a buscar
  * valores que están en la tabla
  * 
  */
  search(): void {
    this.rows = this.rows2.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValue.toString()
          .toLowerCase());
    });
  }

  getTotalText() {
    return this.rows2.length == 1 ? "Registro" : "Registros";
  }

  /**
  * Método para abrir modales según la acción
  * 
  * @param button 
  * @param row 
  */
  openModal(button: string, row: InsuranceRatesModel = null) {

    const modalRef = this.modalService.open(InsuranceRatesModalComponent, {
      size: "lg"
    });

    modalRef.componentInstance.button = button;
    modalRef.componentInstance.data = row;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.getInsuranceRates();
      }

    });

  }

  // Methods Rest

  /**
   * Método que consume un servicio para obtener los datos de la 
   * tabla SE_INSURANCE_RATES_MODEL
   * 
   */
  getInsuranceRates() {

    this.insuranceRatesService.getInsuranceRates().subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        let priceMasterResponse = response.body as InsuranceRatesResponse;

        priceMasterResponse.data.map((resourceMap, configError) => {

          let dto: InsuranceRatesModel = resourceMap;

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

    });

  }


}

