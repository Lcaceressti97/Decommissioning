import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InsuranceRatesResponse } from 'src/app/entities/respopnse';
import { MonthlyFeesModel } from 'src/app/models/MonthlyFeesModel';
import { InsuranceConsultationService } from 'src/app/services/insurance-consultation.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-insurance-consultation',
  templateUrl: './insurance-consultation.component.html',
  styleUrls: ['./insurance-consultation.component.css']
})
export class InsuranceConsultationComponent implements OnInit {


  inputClasses = "my-auto";

  rows: MonthlyFeesModel[] = [];
  rows2: MonthlyFeesModel[] = [];
  loadingIndicator: boolean = true;
  private sub: any;
  phone: any
  loading = false;
  mostrarTabla = false;
  infoCargado = false;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  consultForm!: FormGroup;

  constructor(private insuranceConsultationService: InsuranceConsultationService,
    private modalService: NgbModal,
    public utilService: UtilService,
    private activatedRoute: ActivatedRoute,
    private router: Router, private readonly fb: FormBuilder) {
  }

  ngOnInit() {
    this.consultForm = this.initForm();

  }

  initForm(): FormGroup {
    return this.fb.group({
      model: ['', [Validators.required]],
    })
  }


  //* COMPONENTS
  /**
 * Método encargado de obtener las cuetas por id
 * 
 */
  getMonthlyFeesByModel(model: any) {
    this.insuranceConsultationService.getMonthlyFeesByModel(model).subscribe((response) => {
  
      if (response.status === 200) {
  
        this.rows = [];
        this.rows2 = [];
  
        const insuranceRatesResponse = response.body as InsuranceRatesResponse;
  
        insuranceRatesResponse.data.map((dataOk) => {
  
          let dto: MonthlyFeesModel = dataOk;
          this.rows.push(dto);
  
        });
  
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;
  
        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }
  
      } else {
        this.utilService.showNotification(1, "No se encontraron datos para el modelo ingresado.!!");
        this.rows = [];
        this.rows2 = [];
      }
  
    }, (error) => {
      this.utilService.showNotification(1, "No se encontraron datos para el modelo ingresado.!!");
      this.rows = [];
      this.rows2 = [];
    });
  }

  //* UTILS
  search() {
    this.rows = this.rows2.filter((rowsInsuranceConsultation) => {
      return JSON.stringify(rowsInsuranceConsultation)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }
}
