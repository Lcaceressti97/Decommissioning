import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
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


    if (error.status === 500 || error.status === 502 || error.status === 504) {
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
   * Método que consume un servicio rest para traer todas
   * las sucursales
   * 
   * @returns 
   */
  getAllBrancheOffices(page:any, size:any): Observable<any> {
    return this.http.get(`${this.localUrl}branchoffices`, {
      observe: "response",
      headers: this.headers,
      params:{
        page: page,
        size: size
      }
    });
  }


  /*
* Método encargado de realizar una petición http para traer la info
* de las bodegas
* 
* @returns 
*/
  getWareHouses(): Observable<any> {
    return this.http.get(`${this.localUrl}warehousemaintenance`, {
      observe: "response",
      headers: this.headers
    });
  }

  /*
* Método encargado de realizar una petición http para traer la info
* de los usuarios que tienen permiso para emitir facturas
* 
* @returns 
*/
  getIssuingUsers(): Observable<any> {
    return this.http.get(`${this.localUrl}controluserpermissions/issuingusers`, {
      observe: "response",
      headers: this.headers
    });
  }


  /**
   * Método que consume un servicio rest para obtener los registros 
   * 
   */
  getByFilter(invoiceStatus: any, warehouse: string, agency: string, createdBy: string): Observable<any> {
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
      params = params.set('userIssued', createdBy);
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
   * Método que consume un servicio rest el cual nos devuelve los detalles
   * de una factura.
   * 
   */
     getInvoiceDetailById(id:any): Observable<any>  {
      return this.http.get(`${this.localUrl}invoicedetail/details/${id}`, {
        observe: "response",
        headers: this.headers,
      }).pipe(
        catchError((err)=>
        this.handleError(err, "No se ha podido obtener los detalles de la factura"))
      );
    }


}
