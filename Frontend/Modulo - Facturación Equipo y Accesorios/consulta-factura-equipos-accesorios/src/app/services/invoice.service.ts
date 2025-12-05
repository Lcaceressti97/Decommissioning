import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { Billing } from '../models/billing';
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
    

    if(error.status===500 || error.status === 502 || error.status === 504){
      this.utilService.showNotifyError(
        error.status,
        `${message}. Contacte al administrador del sistema.`
      );
    }else{
      this.utilService.showNotifyError(
        error.status
      );
    }


    return throwError(error);
  }

  // Resources

  
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

   /**
    * Método encargado de traer las facturas del día
    * 
    */
   getBilling(page: number = 0, size: number = 10, status:any): Observable<any> {
    return this.http.get(`${this.localUrl}billing`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page.toString(),
        size: size.toString(),
        status:status
      }
    }).pipe(
      catchError((err)=>
      this.handleError(err, "No se ha podido obtener los registros de las facturas"))
    );
   }
 
  /**
   * Método que consume un servicio rest para obtener la infomarción
   * de todas las facturas
   * 
   */
  getInvoiceAll(): Observable<any> {
    return this.http.get(`${this.localUrl}billing`, {
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((err)=>
      this.handleError(err, "No se ha podido obtener los registros de las facturas"))
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


  
    /**
     * Método que trae como paginación los datos 
     * 
     * @param page 
     * @param size 
     * @returns 
     */
    getInvoicePagination(page:any, size:any): Observable<any> {
      return this.http.get(`${this.localUrl}billing/invoicesbypagination`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page,
          size: size
        }
      }).pipe(
        catchError((err)=>
        this.handleError(err, "No se ha podido obtener los registros de las facturas"))
      );
    }
    

  
    /**
     * Método que trae los datos por un rango de fecha
     * 
     * @param page 
     * @param size 
     * @returns 
     */
    getBillisByDateRange(page: number = 0, size: number = 10, initDate:any, ednDate:any): Observable<any> {
      return this.http.get(`${this.localUrl}billing/daterange`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page.toString(),
          size: size.toString(),
          startDate: initDate,
          endDate: ednDate
        }
      }).pipe(
        catchError((err)=>
        this.handleError(err, "No se ha podido obtener los registros de las facturas"))
      );
    }

    /**
     * Servicio que consume una api rest para traer las facturas
     * por filtro
     * 
     */
    getBillisByFilter(page: number = 0, size: number = 10,type = null, value : string): Observable<any> {
      return this.http.get<Billing[]>(`${this.localUrl}billing/findbyfilter/${value}/${type}`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page.toString(),
          size: size.toString()
        }
      }).pipe(
        catchError((err)=>
        this.handleError(err, "No se ha podido obtener los registros de las facturas"))
      );
    }


    /**
     * Método que consume un servicio rest para obtener la
     * información de la sucursal según su identificador
     * 
     * @param id 
     * @returns 
     */
    getBrancheById(id:any): Observable<any>  {
      return this.http.get(`${this.localUrl}branchoffices/${id}`, {
        observe: "response",
        headers: this.headers,
      }).pipe(
        catchError((err)=>
        this.handleError(err, "No se ha podido obtener los detalles de las facturas"))
      );
    }

    /**
     * Método que consume un servicio rest para actualizar la 
     * factura a exonerado
     * 
     * @param id 
     * @param correlativeOrdenExemptNo 
     * @param correlativeCertificateExoNo 
     * @returns 
     */
    putUpdateExonerado(id:any,data: any): Observable<any> {

      return this.http.put(`${this.localUrl}billing/updatecorporateclient/${id}`,data, {

      }).pipe(
        catchError((err)=>
        this.handleError(err, "No se puedo exonerar la factura"))
      );

    }

    putUpdateExoneradoSimpleClient(id:any,data:any): Observable<any> {

      return this.http.put(`${this.localUrl}billing/updatesingleclient/${id}`, data,{

      }).pipe(
        catchError((err)=>
        this.handleError(err, "No se puedo exonerar la factura"))
      );

    }

    /**
     * Método que consume una api para enviar la factura por correo
     * que se ingreso
     * 
     * @param id 
     * @param data 
     * @returns 
     */
    postSendEmail(data:any): Observable<any> {

      return this.http.post(`${this.localUrl}sendemail`, data,{
        observe: 'response',
        headers: this.headers,
      }).pipe(
        catchError((err)=>
        this.handleError(err, "No se puedo enviar el correo"))
      );

    }


}
