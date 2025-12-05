import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  // Props
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

  /**
* Método encargado de realizar una petición http para traer la info
* de la factura por el id de la factura
* 
* @param id 
* @returns 
*/
  getbillingById(id: any): Observable<any> {
    return this.http.get(`${this.localUrl}billing/${id}`, {
      observe: "response",
      headers: this.headers
    });
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


  /**
 * Método encargado de realizar una petición http para traer loa
 * registros de control de las facturas
 * 
 * @param id 
 * @returns 
 */
  getControlAuthEmissionByStatus(page?: any, size?: any, status?: any): Observable<any> {
    return this.http.get(`${this.localUrl}controlauthemission/searchByTypeApproval`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size,
        typeApproval: status
      }
    });
  }


  /**
 * Método encargado de realizar una petición http para traer loa
 * registros de control de las facturas
 * 
 * @param id 
 * @returns 
 */
  getControlCancellationByStatus(page?: any, size?: any, status?: any): Observable<any> {
    return this.http.get(`${this.localUrl}controlcancellation/searchbytypecancellation`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size,
        typeCancellation: status
      }
    });
  }

}
