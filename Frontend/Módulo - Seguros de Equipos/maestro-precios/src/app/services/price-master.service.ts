import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { PriceMasterModel } from '../models/model';

@Injectable({
  providedIn: 'root'
})
export class PriceMasterService {

  private readonly localUrl = "/apiEquipmentInsurance/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  // Handler que controla los codeHttp

  handleError(error: HttpErrorResponse, message: string = "") {

    //console.log(error);

    if (error.status === 500 || error.status === 504 || error.status === 400) {
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


  constructor(private http: HttpClient, private utilService: UtilService) { }


  // Methods Services Rest
  /**
  * Método que consume un servicio REST para obtener la información de los precios maestros
  * 
  * @returns 
  */
  getPriceMaster(): Observable<any> {
    return this.http.get(`${this.localUrl}price-master`, {
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((err) => this.handleError(err, "No se ha podido cargar los datos"))
    );
  }


  /**
 * Método que consume un servicios rest para crear
 * un nuevo canal
 * 
 * @param data 
 * @returns 
 */
  postPriceMaster(data: PriceMasterModel): Observable<any> {
    return this.http.post(`${this.localUrl}price-master/add`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido crear el precio maestro"))
    );
  }

  /**
   * Método que consume un servicios rest para actualizar
   * el canal
   * 
   * @param data 
   * @returns 
   */
  putPriceMaster(id: any, data: PriceMasterModel): Observable<any> {
    return this.http.put(`${this.localUrl}price-master/update/${id}`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el precio maestro"))
    );
  }


}
