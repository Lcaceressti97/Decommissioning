import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { BillingServicesModel } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class JsonService {

  // Props
  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }

  handleError(error: HttpErrorResponse, message: string = "") {


    if (error.status === 500 || error.status === 502 || error.status === 504 || error.status === 400) {
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

  // CRUD

  /**
   * Método que consume un servicio rest para obtener todos los 
   * servicios de facturación
   * 
   */
  getBillingServiceByPagination(page:any, size:any): Observable<any> {
    return this.http.get(`${this.localUrl}billingservices`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size
      }
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los registros de los servicios de facturación"))
    );
  }

  /**
   * Método que consume un servicios rest para crear
   * un nuevo servicio de facturación
   * 
   * @param data 
   * @returns 
   */
  postBillingService(data: BillingServicesModel): Observable<any> {
    return this.http.post(`${this.localUrl}billingservices/add`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido crear el servicio de facturación"))
    );
  }

  /**
   * Método que consume un servicios rest para actualizar
   * el servicio de facturación
   * 
   * @param data 
   * @returns 
   */
  putBillingService(id:any, data: BillingServicesModel): Observable<any> {
    return this.http.put(`${this.localUrl}billingservices/update/${id}`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el servicio de facturación"))
    );
  }


}
