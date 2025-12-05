import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { ChannelModel } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  // Props

  // Url And Header
  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
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
  * Método que consume un servicio REST para obtener la información de los canales
  * que se utilizan en los procesos de facturación
  * 
  * @returns 
  */
  getChannels(page: any, size: any): Observable<any> {
    return this.http.get(`${this.localUrl}channel`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size
      }
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
  postChannel(data: ChannelModel): Observable<any> {
    return this.http.post(`${this.localUrl}channel/add`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido crear el canal"))
    );
  }

  /**
   * Método que consume un servicios rest para actualizar
   * el canal
   * 
   * @param data 
   * @returns 
   */
  putChannel(id: any, data: ChannelModel): Observable<any> {
    return this.http.put(`${this.localUrl}channel/update/${id}`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el canal"))
    );
  }


}
