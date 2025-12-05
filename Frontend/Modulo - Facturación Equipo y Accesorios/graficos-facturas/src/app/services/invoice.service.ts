import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { FilterModel } from '../model/model';


@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }

  handleError(error: HttpErrorResponse, message: string = "") {


    if (error.status >= 500) {
      this.utilService.showNotifyError(
        error.status,
        `${message}. Contacte al administrador del sistema.`
      );
    } else {
      this.utilService.showNotifyError(
        error.status
      );
    }


    return throwError(error);
  }


  /**
 * Método que trae los datos por un rango de fecha
 * 
 * @param page 
 * @param size 
 * @returns 
 */
  getBillisByDateRange(initDate: any, ednDate: any): Observable<any> {
    return this.http.get(`${this.localUrl}billing/invoicedetailsgraph`, {
      observe: "response",
      headers: this.headers,
      params: {
        startDate: initDate,
        endDate: ednDate
      }
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los registros de Facturación"))
    );
  }

  getBillisByFilter(initDate: any, ednDate: any, idBranchOffices: any, invoiceType: any, status: any): Observable<any> {

    let url: string = `billing/invoicedetailsgraph?startDate=${initDate}&endDate=${ednDate}`;

    if (idBranchOffices === null) {
      url = url + `&idBranchOffices=`;
    } else {
      url = url + `&idBranchOffices=${idBranchOffices}`;
    }

    if (invoiceType === null) {
      url = url + `&invoiceType=`;
    } else {
      url = url + `&invoiceType=${invoiceType}`;
    }

    if (status === null) {
      url = url + `&status=`;
    } else {
      url = url + `&status=${status}`;
    }

    return this.http.get(`${this.localUrl}${url}`, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los registros de Facturación"))
    );
  }

  /**
   * Método que consume un servicio rest para traer todas
   * las sucursales
   * 
   * @returns 
   */
  getAllBrancheOffices(page: any, size: any): Observable<any> {
    return this.http.get(`${this.localUrl}branchoffices`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size
      }
    });
  }

  /**
* Método que consume un servicio rest para traer todas
* las sucursales
* 
* @returns 
*/
  getBrancheOfficesByUser(user: any): Observable<any> {
    return this.http.get(`${this.localUrl}branchoffices/report`, {
      observe: "response",
      headers: this.headers,
      params: {
        user: user,
      }
    });
  }

  /*
* Método encargado de realizar una petición http para traer la info
* de las bodegas
* 
* @returns 
*/
  getWareHouses(user: any): Observable<any> {
    return this.http.get(`${this.localUrl}warehousemaintenance/report`, {
      observe: "response",
      headers: this.headers,
      params: {
        user: user
      }
    });
  }

  /**
   * Método que consume un servicio rest
   * para obtener los valores númericos
   * de todos los registros de facturas
   * 
   * @param payload 
   * @returns 
   */
  getBillingByFilter(payload: FilterModel): Observable<any> {
    return this.http.post(`${this.localUrl}billing/invoicedetailsgraph`, payload, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los registros"))
    );
  }

}
