import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AmnetBalanceEntity } from 'src/app/entities/AmnetBalanceEntity';
import { NavegaBalanceEntity } from 'src/app/entities/NavegaBalanceEntity';
import { AmnetBalance } from 'src/app/models/AmnetBalance';
import { NavegaBalance } from 'src/app/models/NavegaBalance';
import { TableNameModel } from 'src/app/models/TableNameModel';
import { BalanceInquiryAmsysService } from 'src/app/services/balance-inquiry-amsys.service';
import { UtilsService } from 'src/app/services/utils.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-balance-inquiry-amsys',
  templateUrl: './balance-inquiry-amsys.component.html',
  styleUrls: ['./balance-inquiry-amsys.component.css']
})
export class BalanceInquiryAmsysComponent implements OnInit {

  navegaBalance = [];
  rowsNavega = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  private sub: any;
  currentPage = 0;
  pageSize = 2000;
  detailOptions: any[] = [];
  selectedDetailOption: TableNameModel | null = null;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-3";

  constructor(private balanceInquiryAmsysService: BalanceInquiryAmsysService,
    public utilService: UtilsService,
    private activatedRoute: ActivatedRoute) {

    this.activatedRoute.queryParams.subscribe(params => {
      this.sub = JSON.stringify(params['sub']);

      if (this.sub)
        sessionStorage.setItem('sub', this.sub)
    });
  }

  ngOnInit(): void {
    let permissions = this.loadPermissions();
    this.getDetailOptions();

  }

  //* COMPONENTS
  loadPermissions() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permissions = subs[2]
    return permissions.split('_')
  }

  // Método para obtener las opciones de detalle
  getDetailOptions() {
    this.balanceInquiryAmsysService.getDetailOptions().subscribe((options) => {
      this.detailOptions = options.data;
      this.getBalances();
    });
  }


  onDetailChange() {
    this.loadingIndicator = true;
    this.navegaBalance = [];
    this.rowsNavega = [];
  
    // Muestra el spinner de carga
    Swal.fire({
      title: 'Cargando Información...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });
  
    this.getBalances();
  }

  getBalances() {
    this.balanceInquiryAmsysService.getBalanceDetail(this.selectedDetailOption.tableName).subscribe((res) => {
      // Cierra el spinner
      Swal.close();
  
      if (res.code === 0) {
        this.loadingIndicator = false;
  
        let data = Array.isArray(res.data) ? res.data : [res.data];
        this.navegaBalance = data.map((v) => {
          let dto = {} as NavegaBalance;
          dto.id = v.id;
          dto.customerName = v.customerName;
          dto.customerNo = v.customerNo;
          dto.customerCode = v.customerCode;
          dto.product = v.product;
          dto.ebsAccount = v.ebsAccount;
          dto.idOrganization = v.idOrganization;
          dto.organizationName = v.organizationName;
          dto.currency = v.currency;
          dto.balance = v.balance;
          dto.closingDate = v?.closingDate;
  
          return dto;
        });
        this.rowsNavega = [...this.navegaBalance];
        this.updateRowsBasedOnType();
      } else {
        this.utilService.showNotificationError(res.code);
      }
    }, (error) => {
      // Cierra el spinner en caso de error
      Swal.close();
      this.utilService.showNotificationError(error);
    });
  }



  //* UTILS
  searchNavegaBalances() {
    this.navegaBalance = this.rowsNavega.filter((navegaBalance) => {
      return JSON.stringify(navegaBalance)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  updateRowsBasedOnType() {
    this.rowsNavega = [...this.navegaBalance];
  }

  getTotalText() {
    return this.rowsNavega.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getDetailOptions();
  }

  
  formatDate(date: string | null): string {
    if (!date) {
      return '';
    }

    const formattedDate = new Date(date);
    const day = formattedDate.getDate().toString().padStart(2, '0');
    const month = (formattedDate.getMonth() + 1).toString().padStart(2, '0');
    const year = formattedDate.getFullYear();
    let hours = formattedDate.getHours();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12;
    const minutes = formattedDate.getMinutes().toString().padStart(2, '0');
    const seconds = formattedDate.getSeconds().toString().padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds} ${ampm}`;
  }
}
