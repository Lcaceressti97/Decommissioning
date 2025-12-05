import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { InvoiceEquipmentAccesoriesEntity } from '../entities/InvoiceEquipmentAccesoriesEntity';
import { UtilService } from './util.service';
import { InvoiceEquipmentAccesories } from '../models/InvoiceEquipmentAccesories';

@Injectable({
  providedIn: 'root'
})
export class BillingReportService {

  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }

  handleError(error: HttpErrorResponse, message: string = "") {
    this.utilService.showNotification(
      2,
      `${message}. Contacte al administrador del sistema.`
    );
    return throwError(error);
  }

  getByFilter(invoiceStatus = null, warehouse = null, agency = null, createdBy = null): Observable<any> {
    return this.http
      .get<InvoiceEquipmentAccesoriesEntity[]>(`${this.localUrl}invoiceequipmentaccessories/filter`, {
        observe: "response",
        headers: this.headers,
        params: {
          invoiceStatus: invoiceStatus ? invoiceStatus : "",
          warehouse: warehouse ? warehouse : "",
          agency: agency ? agency : "",
          createdBy: createdBy ? createdBy : ""
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener la información")
        )
      );
  }

  getByFilter2(invoiceStatus: string, warehouse: string, agency: string, createdBy: string): Observable<InvoiceEquipmentAccesoriesEntity[]> {

    let params = new HttpParams();
    if (invoiceStatus) {
      params = params.set('invoiceStatus', invoiceStatus);
    }
    if (warehouse) {
      params = params.set('warehouse', warehouse);
    }
    if (agency) {
      params = params.set('agency', agency);
    }
    if (createdBy) {
      params = params.set('createdBy', createdBy);
    }

    return this.http.get<InvoiceEquipmentAccesoriesEntity[]>(`${this.localUrl}invoiceequipmentaccessories/filter`, {

      params
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener la información")
      )
    );;
  }

  getByFilter3(invoiceStatus: any, warehouse: string, agency: string, createdBy: string): Observable<any> {
    let params = new HttpParams();
    if (invoiceStatus || invoiceStatus !== null) {
      params = params.set('status', invoiceStatus);
    }

    if (warehouse) {
      params = params.set('warehouse', warehouse);
    }

    if (agency) {
      params = params.set('agency', agency);
    }
    
    if (createdBy) {
      params = params.set('authorizingUser', createdBy);
    }

    return this.http
      .get(`${this.localUrl}billing/filterinvoices`, {
        observe: "response",
        params: params
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener la información")
        )
      );
  }


  /**
   * Método encargado de consumir un servicio rest para obtener la información según los filtros
   * 
   * @param invoiceStatus 
   * @param warehouse 
   * @param agency 
   * @param createdBy 
   * @returns 
   */
  getByFilterFinal(invoiceStatus: any, brancheOffice: any, warehouse: any, createdBy: any): Observable<any> {
    let params = new HttpParams();
    if (invoiceStatus !== null) {
      params = params.set('status', invoiceStatus);
    }

    if (warehouse!==null) {
      params = params.set('warehouse', warehouse);
    }

    if (brancheOffice!==null) {
      params = params.set('agency', brancheOffice);
    }
    
    if (createdBy!==null) {
      params = params.set('authorizingUser', createdBy);
    }

    return this.http
      .get(`${this.localUrl}billing/filterinvoices`, {
        observe: "response",
        params: params
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener la información")
        )
      );
  }
  

    /*
  * Método encargado de realizar una petición http para traer la info
  * de la factura por el id de la factura
  * 
  * @param id 
  * @returns 
  */
    configparametersById(id: any): Observable<any> {
      return this.http.get(`${this.localUrl}configparameters/searchById`, {
        observe: "response",
        headers: this.headers,
        params: {
          idApplication: id
        }
      });
    }


}
