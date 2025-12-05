import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { PriceMasterModel } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class EquipmentAccesoriesService {

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

  // Apis

  /**
   * Método que consume un servicio rest para obtener todos los precios
   * mestros
   * 
   * @returns 
   */
  getPricesMaster(page: any, size: any): Observable<any> {
    return this.http.get(`${this.localUrl}pricemaster`, {
      observe: 'response',
      headers: this.headers,
      params: {
        page: page,
        size: size
      }
    });
  }

  /**
   * Método que consume un servicio rest para obtener el precio
   * mestro por el modelo
   * 
   * @returns 
   */
  getPricesMasterByModel(model: any): Observable<any> {
    return this.http.get(`${this.localUrl}pricemaster/model/${model}`, {
      observe: 'response',
      headers: this.headers
    });
  }

  /**
 * Método que consume un servicio rest para obtener el precio
 * mestro por el modelo
 * 
 * @returns 
 */
  getPricesMasterModelByModel(model: any): Observable<any> {
    return this.http.get(`${this.localUrl}pricemaster/maintenance/${model}`, {
      observe: 'response',
      headers: this.headers
    });
  }

  /**
   * Método encargado de crear un precio maestro a través de un servicio
   * rest
   * 
   * @param data 
   * @returns 
   */
  postPriceMaster(data: PriceMasterModel): Observable<any> {
    return this.http.post(`${this.localUrl}pricemaster/add`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido crear el precio maestro"))
    );
  }

  /**
 * Método encargado de actualizar un precio maestro a través de un servicio
 * rest
 * 
 * @param data 
 * @returns 
 */
  putPriceMaster(id: any, data: PriceMasterModel): Observable<any> {
    return this.http.put(`${this.localUrl}pricemaster/update/${id}`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el precio maestro"))
    );
  }



}
